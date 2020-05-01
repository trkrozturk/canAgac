package com.can.tree;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.can.tree.logsHelper.LogsGenerate;
import com.can.tree.logsHelper.SalesLogs;
import com.can.tree.model.Customers;
import com.can.tree.model.ExpectedPayments;
import com.can.tree.model.Measurement;
import com.can.tree.model.Notes;
import com.can.tree.model.OccuredPayments;
import com.can.tree.model.Qr;
import com.can.tree.model.ResponseObject;
import com.can.tree.model.Sales;
import com.can.tree.model.Tree;
import com.can.tree.model.TreeObject;
import com.can.tree.model.User;
import com.can.tree.modelHelper.BodyHeight;
import com.can.tree.modelHelper.BodyPerimeter;
import com.can.tree.modelHelper.Height;
import com.can.tree.qrGenarate.QrGenarate;
import com.can.tree.query.FilterHelper;
import com.can.tree.query.QueryClass;
import com.can.tree.service.CustomersService;
import com.can.tree.service.ExpectedPaymentsService;
import com.can.tree.service.LogsService;
import com.can.tree.service.OccuredPaymentsService;
import com.can.tree.service.QrService;
import com.can.tree.service.UserService;
import com.can.tree.treeService.MeasurementService;
import com.can.tree.treeService.NotesService;
import com.can.tree.treeService.SalesService;
import com.can.tree.treeService.TreeObjectService;
import com.can.tree.treeService.TreeService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.DocumentException;;




@RestController
public class Controller {

	private static final Logger logger = LoggerFactory.getLogger(Controller.class);
	private UserService userService;
	private QrService qrService;
	private TreeService treeService;
	private TreeObjectService treeObjectService;
	private MeasurementService measurementService;
	private SalesService salesService;
	private CustomersService customerService;
	private NotesService notesService;
	private LogsService logsService;
	private ExpectedPaymentsService expectedPaymentsService;
	private OccuredPaymentsService occuredPaymentservice;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	JsonParser parser = new JsonParser();

	
	public Controller(UserService userService,ExpectedPaymentsService expectedPaymentsService,OccuredPaymentsService occuredPaymentservice,LogsService logsService,BCryptPasswordEncoder bCryptPasswordEncoder,QrService qrService,TreeService treeService,TreeObjectService treeObjectService,MeasurementService measurementService,SalesService salesService,CustomersService customerService, NotesService notesService) {
    	this.userService = userService;
    	this.qrService = qrService;
    	this.treeService = treeService;
    	this.treeObjectService = treeObjectService;
    	this.measurementService = measurementService;
    	this.salesService = salesService;
    	this.customerService=customerService;
    	this.notesService=notesService;
    	this.logsService=logsService;
    	this.expectedPaymentsService=expectedPaymentsService;
    	this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    	this.occuredPaymentservice= occuredPaymentservice;
    	System.out.println("Controller constructor"); 
	}
	
    
	@RequestMapping(value = "/signUp", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String signUp(@RequestBody User user) {
        ResponseObject resObj = new ResponseObject();		
		if(user != null) {
			if(user.getUsername() != null && !user.getUsername().isEmpty()) {
				if(user.getPassword() != null && !user.getPassword().isEmpty()) {
					if(user.getPassword().length()>=6) {
							if(user.getFullName() != null && !user.getFullName().isEmpty()) {
								
									List<User> existingUsers = userService.findByUserName(user.getUsername(), PageRequest.of(0, 1)).getContent();
									if(existingUsers.size()>0) {
										resObj.setStatus(ResponseObject.BAD_REQUEST);
										resObj.setMsg("Kullanıcı adı sistemde mevcut");
										logger.info("Kullanıcı adı sistemde mevcut: " + user.getUsername());
										return resObj.toString();
									}
									
									
									user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
							        User newUser = userService.save(user);
							        logger.info("User yaratıldı");
							      
							        
							        
							        
							        resObj.setStatus(ResponseObject.OK);
							        resObj.addData(newUser.toJson());
							        return resObj.toString();
								
					}else {
						resObj.setStatus(ResponseObject.BAD_REQUEST);
						resObj.setMsg("Lütfen İsim-Soyisim bilgisini giriniz.");
						logger.info("İsim eksik");
						}
					}else {
						resObj.setStatus(ResponseObject.BAD_REQUEST);
						resObj.setMsg("Şifre en az 6 karakter uzunluğunda olmalıdır.");
						logger.info("şifre 6 karakter eksik");
					}
				}else {
					resObj.setStatus(ResponseObject.BAD_REQUEST);
					resObj.setMsg("Lütfen bir şifre giriniz.");
					logger.info("şifre eksik");
				}
			}else {
				resObj.setStatus(ResponseObject.BAD_REQUEST);
				resObj.setMsg("Lütfen bir kullanıcı adı giriniz.");
				logger.info("username eksik");
			}
		}else {
			resObj.setStatus(ResponseObject.BAD_REQUEST);
			resObj.setMsg("Lütfen eksik alanları doldurunuz.");
			logger.info("User null");
		}
		return resObj.toString();
    }
	@RequestMapping(value = "/filterList", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String filterList(@RequestBody String filterObj) throws InterruptedException, ExecutionException, ParseException {
		//String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //User user = userService.findByUserName(username, PageRequest.of(0, 1)).getContent().get(0)
		FilterHelper resp = new FilterHelper(parser.parse(filterObj).getAsJsonObject());
		return resp.getResponse();
    }
	@RequestMapping(value = "/trk", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String trk(HttpServletRequest req, HttpServletResponse response) {
		//String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //User user = userService.findByUserName(username, PageRequest.of(0, 1)).getContent().get(0)
		String asd ="";
		 Iterable<User> test = userService.findAll();

     for(Object o : test){
         //System.out.println(o.toString());
         asd=asd+"\n"+o.toString();
     }
		return asd;
    }
	
	@RequestMapping(value = "/qrGenerate", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public ResponseEntity<Object> qrGenerate(@RequestBody String countObj) throws FileNotFoundException {
		 Iterable<Qr> test = qrService.findAll();
		 JsonObject resp = new JsonObject();
		 JsonObject obj = new JsonObject();
		 obj= parser.parse(countObj).getAsJsonObject();
		 String count = obj.get("count").getAsString();
		 logger.info("qrGenerate count : "+count);
		 String lastValue = "";
		 for(Qr o : test){
			 String val = o.toString();
			 resp = parser.parse(val).getAsJsonObject();
	         lastValue = resp.get("lastQrNumber").getAsString();
	        }
		 Qr updateQr = new Qr();
		 updateQr.setId(resp.get("id").getAsString());
		 updateQr.setTotalQr(resp.get("totalQr").getAsString());
		 updateQr.setLastQrNumber(resp.get("lastQrNumber").getAsString());
		 updateQr.addNumberToLastNumber(count);
		 updateQr.updateTotalCount(count);
		 qrService.save(updateQr);
		QrGenarate generator = new QrGenarate();
		try {
			generator.generate(Integer.parseInt(count), lastValue);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return generator.qrPdfDownload();
    }
	@RequestMapping(value = "/qrCount", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String qrCount() {
		ResponseObject respObj = new ResponseObject();
		 Iterable<Qr> test = qrService.findAll();
		 String totalCount="0";
		 for(Qr o : test){
			 totalCount = o.getTotalQr();
	        }
		 JsonObject temp = new JsonObject();
		 temp.addProperty("count", totalCount);
		 respObj.setStatus(ResponseObject.OK);
		 respObj.addData(temp);
        return respObj.toString();
    }
	//@CrossOrigin 
	@RequestMapping(value = "/addTree", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String addTree(@RequestBody Tree tree) {
		logger.info("SERVICE : addTree");
		  ResponseObject resObj = new ResponseObject();		
			if(tree != null) {
				if(tree.getType()!= null && !tree.getType().isEmpty()) {
									if(tree.getTypeLatin() != null && !tree.getTypeLatin().isEmpty()) {
									
										List<Tree> existingTree = treeService.findByType(tree.getType(), PageRequest.of(0, 1)).getContent();
										List<Tree> existingTree2 = treeService.findByTypeLatin(tree.getTypeLatin(), PageRequest.of(0, 1)).getContent();
										if(existingTree.size()>0) {
											resObj.setStatus(ResponseObject.BAD_REQUEST);
											resObj.setMsg("Bu Ağaç türü sistemde mevcut");
											logger.info("Ağaç türü sistemde mevcut: " + tree.getType());
											return resObj.toString();
										}
										
										if(existingTree2.size()>0) {
											resObj.setStatus(ResponseObject.BAD_REQUEST);
											resObj.setMsg("Bu Ağaç türü sistemde mevcut(Latince Ad Aynı)");
											logger.info("Ağaç türü sistemde mevcut(Latince Ad Aynı): " + tree.getTypeLatin());
											return resObj.toString();
										}
										
										
								        Tree newTree = treeService.save(tree);
								        logger.info("Ağaç yaratıldı");
								        resObj.setStatus(ResponseObject.OK);
								        resObj.addData(newTree.toJson());
								        return resObj.toString();
									}
									else {
										resObj.setStatus(ResponseObject.BAD_REQUEST);
										resObj.setMsg("Lütfen Latince Adı bilgisini giriniz.");
										logger.info("Latince Adı bilgisi eksik");
									}
					
				}else {
					resObj.setStatus(ResponseObject.BAD_REQUEST);
					resObj.setMsg("Lütfen bir ağaç türü giriniz.");
					logger.info("Ağaç türü eksik");
				}
			}else {
				resObj.setStatus(ResponseObject.BAD_REQUEST);
				resObj.setMsg("Lütfen eksik alanları doldurunuz.");
				logger.info("User null");
			}
			return resObj.toString();
		
	
	}
	@RequestMapping(value = "/editTree", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String editTree(@RequestBody Tree tree) {
		logger.info("SERVICE : addTree");
		  ResponseObject resObj = new ResponseObject();		
			if(tree != null) {
				if(tree.getType()!= null && !tree.getType().isEmpty()) {
									if(tree.getTypeLatin() != null && !tree.getTypeLatin().isEmpty()) {
									
										List<Tree> existingTree = treeService.findByType(tree.getType(), PageRequest.of(0, 2)).getContent();
										List<Tree> existingTree2 = treeService.findByTypeLatin(tree.getTypeLatin(), PageRequest.of(0, 2)).getContent();
										if(existingTree.size()>1) {
												resObj.setStatus(ResponseObject.BAD_REQUEST);
												resObj.setMsg("Bu Ağaç türü sistemde mevcut");
												logger.info("Ağaç türü sistemde mevcut: " + tree.getType());
												return resObj.toString();
										}
										
										if(existingTree2.size()>1) {
												resObj.setStatus(ResponseObject.BAD_REQUEST);
												resObj.setMsg("Bu Ağaç türü sistemde mevcut(Latince Ad Aynı)");
												logger.info("Ağaç türü sistemde mevcut(Latince Ad Aynı): " + tree.getTypeLatin());
												return resObj.toString();
										}
										Tree newTree = treeService.save(tree);
								        logger.info("Ağaç Güncellendi");
								        resObj.setMsg("Ağaç Güncellendi");
								        resObj.setStatus(ResponseObject.OK);
								        resObj.addData(newTree.toJson());
								        return resObj.toString();										
								        
									}
									else {
										resObj.setStatus(ResponseObject.BAD_REQUEST);
										resObj.setMsg("Lütfen Latince Adı bilgisini giriniz.");
										logger.info("Latince Adı bilgisi eksik");
									}
					
				}else {
					resObj.setStatus(ResponseObject.BAD_REQUEST);
					resObj.setMsg("Lütfen bir ağaç türü giriniz.");
					logger.info("Ağaç türü eksik");
				}
			}else {
				resObj.setStatus(ResponseObject.BAD_REQUEST);
				resObj.setMsg("Lütfen eksik alanları doldurunuz.");
				logger.info("User null");
			}
			return resObj.toString();
		
	
	}
	@RequestMapping(value = "/fetchAllType", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String fetchAllType(@RequestBody String request) {
		//String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //User user = userService.findByUserName(username, PageRequest.of(0, 1)).getContent().get(0)
		//String param = req.getParameter("id");
		JsonObject requestObj = new JsonObject();
		requestObj = parser.parse(request).getAsJsonObject();
		String param=requestObj.get("id").getAsString();
		String resp = new String();
		if(!param.equals("allTree")) {
			resp = treeService.findOne(param).get().toString();
			logger.info("fetchAllType Id : "+param);
			
		}
		else {
			JsonArray test = new JsonArray();
			 for(Tree o : treeService.findAll()){
				 JsonObject temp = new JsonObject();
				 temp.addProperty("value", o.getId());
				 temp.addProperty("label", o.getType());
				 
				 test.add(temp);
		     }
			 logger.info("fetchAllType Id : "+param);
			 resp = test.toString();
		}
		return resp;
    }
	@RequestMapping(value = "/fetchAllTypeWeb", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String fetchAllTypeWeb(@RequestBody String request) {
		//String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //User user = userService.findByUserName(username, PageRequest.of(0, 1)).getContent().get(0)
		//String param = req.getParameter("id");
		JsonObject requestObj = new JsonObject();
		requestObj = parser.parse(request).getAsJsonObject();
		String param=requestObj.get("id").getAsString();
		String resp = new String();
		if(!param.equals("allTree")) {
			resp = treeService.findOne(param).get().toString();
			logger.info("fetchAllTypeWeb Id : "+param);
			
		}
		else {
			JsonArray responseArray = new JsonArray();
			 for(Tree o : treeService.findAll()){
				 
				 responseArray.add(o.toJson());
		     }
			 logger.info("fetchAllTypeWeb Id : "+param);
			 resp = responseArray.toString();
		}
		return resp;
    }
	
	@RequestMapping(value = "/qrRead", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String qrReading(@RequestBody String qrId) {
		String resp = new String();
		JsonObject requestTree = new JsonObject();
		requestTree = parser.parse(qrId).getAsJsonObject();
		String treeObjectId=requestTree.get("id").getAsString();
		Optional<TreeObject> tree = treeObjectService.findOne(treeObjectId);
		
		if(tree.isPresent()) {
			logger.info("Ağaç Object Kayıtlı. Ağaç Object Id : "+treeObjectId);
			requestTree = tree.get().toJson();
			requestTree.add("measurement", QueryClass.lastMeasurement(treeObjectId));
			resp=requestTree.toString();
			return resp;
		}
		else {
			logger.info("Ağaç Object Kayıtlı Değil. Ağaç Object Id: "+treeObjectId);
			return "newRecord";
		}
    }
	
	
	@RequestMapping(value = "/addTreeObject", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String addTreeObject(@RequestBody String requestTreeSt) {
		String resp = new String();
		JsonObject requestTree = new JsonObject();
		requestTree = parser.parse(requestTreeSt).getAsJsonObject();
		//requestTree = requestTree.get("treeData").getAsJsonObject();
		String treeObjectId=requestTree.get("id").getAsString();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("tr"));		 
		Date currentDate = new Date();
		System.out.println(formatter.format(currentDate));  
		TreeObject treeObject = new TreeObject();
		treeObject.setId(requestTree.get("id").getAsString());
		treeObject.setType(requestTree.get("type").getAsString());
		treeObject.setTreeId(requestTree.get("treeId").getAsString());
		treeObject.setSalesId(requestTree.get("salesId").getAsString());
		treeObject.setSalesStatus(requestTree.get("salesStatus").getAsBoolean());
		treeObject.setDisease(requestTree.get("disease").getAsBoolean());
		treeObject.setDestroy(false);
		treeObject.setDiseaseType(requestTree.get("diseaseType").getAsString());
		treeObject.setLastEditDate(formatter.format(currentDate));

		logger.info("Yeni Ağaç Object Kaydı. Ağaç Object Id : "+treeObjectId);
		
		Gson gson = new Gson();  
		JsonObject measurementData = new JsonObject();
		measurementData = requestTree.get("measurement").getAsJsonObject();
		Measurement newMeasurement = new Measurement();
		newMeasurement.setTreeObjectId(treeObjectId);
		if(measurementData.has("height") && measurementData.get("height").isJsonObject()) {
			Height height = gson.fromJson(measurementData.get("height"), Height.class);
			newMeasurement.setHeight(height);
			treeObject.setHeight(height);
		}
		if(measurementData.has("bodyHeight") && measurementData.get("bodyHeight").isJsonObject()) {
			BodyHeight bodyHeight = gson.fromJson(measurementData.get("bodyHeight"), BodyHeight.class);
			newMeasurement.setBodyHeight(bodyHeight);
			treeObject.setBodyHeight(bodyHeight);

		}
		if(measurementData.has("bodyPerimeter") && measurementData.get("bodyPerimeter").isJsonObject()) {
			BodyPerimeter bodyPerimeter = gson.fromJson(measurementData.get("bodyPerimeter"), BodyPerimeter.class);
			newMeasurement.setBodyPerimeter(bodyPerimeter);
			treeObject.setBodyPerimeter(bodyPerimeter);
		}
		if(measurementData.has("pot") && !measurementData.get("pot").getAsString().equals("")) {
			newMeasurement.setPot(measurementData.get("pot").getAsString());
			treeObject.setPot(measurementData.get("pot").getAsString());
		}
		if(measurementData.has("form") && !measurementData.get("form").getAsString().equals("")) {
			newMeasurement.setForm(measurementData.get("form").getAsString());
			treeObject.setForm(measurementData.get("form").getAsString());
		}
		
		newMeasurement.setCreateDate(formatter.format(currentDate));
		treeObject.setCreateDate(formatter.format(currentDate));
		newMeasurement.setEdit(measurementData.get("isEdit").getAsBoolean());
		newMeasurement.setEditReason(measurementData.get("editReason").getAsString());
		logger.info("Yeni Measurement Kaydı. Ağaç Object Id : "+treeObjectId);

		treeObjectService.save(treeObject);
		measurementService.save(newMeasurement);
		
		
		return resp;
    }
	
	@RequestMapping(value = "/updateMeasurement", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String updateMeasurement(@RequestBody String requestMeasurement) {
		
		String resp = new String();
		JsonObject measurement = new JsonObject();
		measurement = parser.parse(requestMeasurement).getAsJsonObject();
		Optional<TreeObject> treeObject = treeObjectService.findOne(measurement.get("treeObjectId").getAsString());
		logger.info("Update Measurement. Ağaç Object Id : "+measurement.get("treeObjectId").getAsString());
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("tr"));		 
		Date currentDate = new Date();
		Gson gson = new Gson();  
		Measurement newMeasurement = new Measurement();
		newMeasurement.setTreeObjectId(measurement.get("treeObjectId").getAsString());
		if(measurement.has("id")) {
			newMeasurement.setId(measurement.get("id").getAsString());
			logger.info("Ağaç Son Değeri Güncellendi");
		}
		if(measurement.has("height") && measurement.get("height").isJsonObject()) {
			Height height = gson.fromJson(measurement.get("height"), Height.class);
			newMeasurement.setHeight(height);
			treeObject.get().setHeight(height);
		}
		if(measurement.has("bodyHeight") && measurement.get("bodyHeight").isJsonObject()) {
			BodyHeight bodyHeight = gson.fromJson(measurement.get("bodyHeight"), BodyHeight.class);
			newMeasurement.setBodyHeight(bodyHeight);
			treeObject.get().setBodyHeight(bodyHeight);
		}
		if(measurement.has("bodyPerimeter") && measurement.get("bodyPerimeter").isJsonObject()) {
			BodyPerimeter bodyPerimeter = gson.fromJson(measurement.get("bodyPerimeter"), BodyPerimeter.class);
			newMeasurement.setBodyPerimeter(bodyPerimeter);
			treeObject.get().setBodyPerimeter(bodyPerimeter);
		}
		if(measurement.has("pot") && !measurement.get("pot").getAsString().equals("")) {

			newMeasurement.setPot(measurement.get("pot").getAsString());
			treeObject.get().setPot(measurement.get("pot").getAsString());
		}
		if(measurement.has("form") && !measurement.get("form").getAsString().equals("")) {
			newMeasurement.setForm(measurement.get("form").getAsString());
			treeObject.get().setForm(measurement.get("form").getAsString());
		}
		newMeasurement.setCreateDate(formatter.format(currentDate));
		treeObject.get().setCreateDate(formatter.format(currentDate));
		treeObject.get().setLastEditDate(formatter.format(currentDate));
		newMeasurement.setEdit(measurement.get("isEdit").getAsBoolean());
		newMeasurement.setEditReason(measurement.get("editReason").getAsString());
		treeObjectService.save(treeObject.get()); 
		measurementService.save(newMeasurement);

		return resp;
    }
	@RequestMapping(value = "/destroyTree", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public String destroyTree(@RequestBody String treeObj) {
        ResponseObject resObj = new ResponseObject();		
        JsonObject tree = new JsonObject();
        tree = parser.parse(treeObj).getAsJsonObject();

    	Optional<TreeObject> treeObject = treeObjectService.findOne(tree.get("id").getAsString());
    	treeObject.get().setDestroy(tree.get("destroy").getAsBoolean());
    	SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("tr"));		 
		Date currentDate = new Date();
		treeObject.get().setLastEditDate(formatter.format(currentDate));
    	treeObjectService.save(treeObject.get());
    	resObj.setMsg("Agaç destroy"+treeObject.get().isDestroy());
    	logger.info("Agaç destroy"+treeObject.get().isDestroy());
        resObj.setStatus(ResponseObject.OK);
        return resObj.toString();
	}
	
	@RequestMapping(value = "/diseaseTree", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public String diseaseTree(@RequestBody String treeObj) {
        ResponseObject resObj = new ResponseObject();		
        JsonObject tree = new JsonObject();
        tree = parser.parse(treeObj).getAsJsonObject();
    	Optional<TreeObject> treeObject = treeObjectService.findOne(tree.get("id").getAsString());
    	treeObject.get().setDisease(tree.get("disease").getAsBoolean());
    	treeObject.get().setDiseaseType(tree.get("diseaseType").getAsString());
    	SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("tr"));		 
		Date currentDate = new Date();
		treeObject.get().setLastEditDate(formatter.format(currentDate));
    	treeObjectService.save(treeObject.get());
    	resObj.setMsg("Agaç Hastalığı : "+treeObject.get().isDisease());
    	logger.info("Agaç Hastalığı : "+treeObject.get().isDisease());

        resObj.setStatus(ResponseObject.OK);
        return resObj.toString();
	}
	
	@RequestMapping(value = "/updateTreeObject", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String updateTreeObject(@RequestBody String requestTreeObject) {
		
		String resp = new String();
		JsonObject treeJsonObject = new JsonObject();
		treeJsonObject = parser.parse(requestTreeObject).getAsJsonObject();
		logger.info("Update TreeObject. Ağaç Object Id : "+treeJsonObject.get("id").getAsString());
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("tr"));		 
		Date currentDate = new Date();
    	Optional<TreeObject> treeObject = treeObjectService.findOne(treeJsonObject.get("id").getAsString());
		treeObject.get().setType(treeJsonObject.get("type").getAsString());
		treeObject.get().setTreeId(treeJsonObject.get("treeId").getAsString());
		treeObject.get().setSalesId(treeJsonObject.get("salesId").getAsString());
		treeObject.get().setSalesStatus(treeJsonObject.get("salesStatus").getAsBoolean());
		treeObject.get().setDisease(treeJsonObject.get("disease").getAsBoolean());
		treeObject.get().setDiseaseType(treeJsonObject.get("diseaseType").getAsString());
		treeObject.get().setLastEditDate(formatter.format(currentDate));
		treeObjectService.save(treeObject.get());
		logger.info("Ağaç Object Update. Ağaç Object Id : "+treeJsonObject.get("id").getAsString());

		return resp;
    }
	
	@RequestMapping(value = "/salesDefine", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String salesDefine(@RequestBody String salesObj) {
        ResponseObject resObj = new ResponseObject();	
        JsonObject salesJson = new JsonObject();
        salesJson = parser.parse(salesObj).getAsJsonObject();
    	String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Sales sales = new Sales();
    	boolean hasNote = salesJson.has("note");
    	String noteString="";
    	if(hasNote) {
    		noteString= salesJson.get("note").getAsString();
    	}
        salesJson.remove("note");
        sales =new Gson().fromJson(salesJson, Sales.class);
    	LogsGenerate log = new SalesLogs(logsService);
        List<Sales> existingSales = salesService.findBySalesCode(sales.getSalesCode(), PageRequest.of(0, 3)).getContent();
        if(existingSales.size()>0) {
			resObj.setMsg("Aynı Satış Kod'lu başka bir satış var.");
			resObj.setStatus(ResponseObject.BAD_REQUEST);
			log.warningAdd(username,"Aynı Satış Kodu Uyarısı" );
			return resObj.toString();
		}
        String customerId = sales.getCustomerId();
        Optional<Customers> customer = customerService.findOne(customerId);
        sales.setStatus(true);
//        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", new Locale("tr"));		 
//		Date currentDate = new Date();
//		sales.setOpenDate(formatter.format(currentDate));
        
		
		sales.setCreator(username);
        if(customer.isPresent()) {
        	sales.setDeletedSales(false);
        	salesService.save(sales);
        	if(hasNote) {
        		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", new Locale("tr"));		 
        		Date currentDate = new Date();
        		List<User> user = userService.findByUserName(username, PageRequest.of(0, 3)).getContent();
        		List<Sales> lastSale = salesService.findBySalesCode(salesJson.get("salesCode").getAsString(), PageRequest.of(0, 3)).getContent();
                Notes note = new Notes();
                note.setNote(noteString);
                note.setCreateDate(formatter.format(currentDate));
                note.setUsername(username);
                note.setFullName(user.get(0).getFullName());
                note.setUserId(user.get(0).getId());
                note.setSalesId(lastSale.get(0).getId());
                notesService.save(note);
        	}
        	resObj.setStatus(ResponseObject.OK);
        	resObj.setMsg("Satış Başarıyla Tanımlandı");
        }
        else {
        	resObj.setMsg("Müşteri Bulunamadı");
			resObj.setStatus(ResponseObject.BAD_REQUEST);
        }
		return resObj.toString();
    }
	@RequestMapping(value = "/editSales", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String editSales(@RequestBody Sales sales) {
        ResponseObject resObj = new ResponseObject();	
    	LogsGenerate log = new SalesLogs(logsService);
        String customerId = sales.getCustomerId();
        List<Sales> existingSales = salesService.findBySalesCode(sales.getSalesCode(), PageRequest.of(0, 3)).getContent();
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(existingSales.size()>1) {
			resObj.setMsg("Aynı Satış Kod'lu başka bir satış var.");
			log.warningEdit(username, "AYNI SATIŞ KODU KULLANILMAKTADIR SATIS ID: "+sales.getId());
			resObj.setStatus(ResponseObject.BAD_REQUEST);
			return resObj.toString();
		}       
        Optional<Customers> customer = customerService.findOne(customerId);
        if(customer.isPresent()) {
        	salesService.save(sales);
        	log.infoEdit(username, "SATIS BASARIYLA GUNCELLENDI SATIS ID: "+sales.getId());
        	resObj.setMsg("Satış Başarıyla Güncellendi");
        	resObj.setStatus(ResponseObject.OK);
        }
        else {
        	resObj.setMsg("Müşteri Bulunamadı");
        	resObj.setStatus(ResponseObject.BAD_REQUEST);
        }
		return resObj.toString();
    }
	@RequestMapping(value = "/editSoldTreeObject", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String editSoldTreeObject(@RequestBody String soldTreeString) {
        ResponseObject resObj = new ResponseObject();	
    	JsonArray soldTreeArray = new JsonArray();
    	JsonObject editRemove = new JsonObject();
    	editRemove = parser.parse(soldTreeString).getAsJsonObject();
    	soldTreeArray = editRemove.get("removeEdit").getAsJsonArray();
    	for(int i=0;i<soldTreeArray.size();i++) {
    		JsonObject temp = new JsonObject();
    		temp = soldTreeArray.get(i).getAsJsonObject();
    		String process = temp.get("process").getAsString();
    		String treeId = temp.get("id").getAsString();
			Optional<TreeObject> treeObject = treeObjectService.findOne(treeId);
    		if(process.equals("remove")) {
    			treeObject.get().setSalesId(null);
    			treeObject.get().setSalesStatus(false);
    			treeObject.get().setPrice(0);
    			treeObjectService.save(treeObject.get());
    			
    		}else if(process.equals("edit")) {
    			treeObject.get().setPrice(temp.get("price").getAsInt());
    			treeObjectService.save(treeObject.get());
    			
    		}
    		else {
    			
    		}
    		
    	}
    	resObj.setStatus(ResponseObject.OK);
		resObj.setMsg("Ağaç Satış Güncellendi");
		return resObj.toString();
    }
	@RequestMapping(value = "/addSalesNote", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String addSalesNote(@RequestBody String salesNotes) {
        ResponseObject resObj = new ResponseObject();
    	LogsGenerate log = new SalesLogs(logsService);
        JsonObject salesNoteObj = new JsonObject();
        salesNoteObj = parser.parse(salesNotes).getAsJsonObject();
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> user = userService.findByUserName(username,  PageRequest.of(0, 1)).getContent();
        try {
			if(QueryClass.salesNoteCount(salesNoteObj.get("salesId").getAsString())>10)
			{
				log.warningAdd(username,  "BU SATIŞTA 10 ADET NOT BULUNMAKTADIR");
				resObj.setMsg("Bu Satışta 10 Adet Mesaj Bulunmaktatır.");
				resObj.setStatus(ResponseObject.BAD_REQUEST);
				return resObj.toString();
			}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Notes newNote = new Notes();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", new Locale("tr"));		 
		Date currentDate = new Date();
		newNote.setUserId(user.get(0).getId());
		newNote.setUsername(username);
		newNote.setFullName(user.get(0).getFullName());
		newNote.setCreateDate(formatter.format(currentDate));
		newNote.setNote(salesNoteObj.get("note").getAsString());
		newNote.setSalesId(salesNoteObj.get("salesId").getAsString());
		resObj.setStatus(ResponseObject.OK);
		resObj.setMsg("Not Başarıyla Eklendi");
		log.infoAdd(username,"NOT BAŞARIYLA EKLENDI");
		notesService.save(newNote);
		return resObj.toString();
      }
	
	@RequestMapping(value = "/editSalesNote", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String editSalesNote(@RequestBody String salesNote) {
        ResponseObject resObj = new ResponseObject();
    	LogsGenerate log = new SalesLogs(logsService);
        JsonObject salesNoteObj = new JsonObject();
        salesNoteObj = parser.parse(salesNote).getAsJsonObject();
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Notes> editNote = notesService.findOne(salesNoteObj.get("noteId").getAsString());
        
        if(!editNote.get().getUsername().equals(username)) {
        	log.warningEdit(username,"NOT DUZENLEME BASKA KULLANICI NOTU : "+salesNoteObj.get("noteId").getAsString());
    		resObj.setStatus(ResponseObject.OK);
    		resObj.setMsg("NOT DUZENLEME BASKA KULLANICININ NOTUNU DEGISTIREMEZSINIZ");
    		return resObj.toString();
        }
        else {
        	editNote.get().setNote(salesNoteObj.get("note").getAsString());
        	notesService.save(editNote.get());
    		log.infoEdit(username,"NOT DUZENLEME BASARILI NOTE ID : "+salesNoteObj.get("noteId").getAsString());
    		resObj.setStatus(ResponseObject.OK);
    		resObj.setMsg("NOT DUZENLEME BASARILI");
    		return resObj.toString();
        }
      }
	
	@RequestMapping(value = "/fetchSalesNotes", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String fetchSalesNotes(@RequestBody String salesNotes) {
		logger.info("SERVICE : fetchSalesNotes");
        ResponseObject resObj = new ResponseObject();
        JsonObject salesNoteObj = new JsonObject();
        JsonArray respArr = new JsonArray();
        salesNoteObj = parser.parse(salesNotes).getAsJsonObject();
        List<Notes> notes = notesService.findBySalesId(salesNoteObj.get("salesId").getAsString(), PageRequest.of(0, 10)).getContent();
        if(notes.size()==0) {
        	resObj.setStatus(ResponseObject.OK);
    		resObj.setMsg("Hiç not bulunamadı");
    		logger.info("Hiç not bulunamadı");
    		return resObj.toString();
        }
        for (Notes notesObj : notes) {
        	respArr.add(notesObj.toJson());
		}
        resObj.setData(respArr);
        resObj.setStatus(ResponseObject.OK);
		resObj.setMsg("Bütün notlar Başarıyla geldi");
		logger.info("Bütün notlar Başarıyla geldi");
		return resObj.toString();
      }
	
	@RequestMapping(value = "/deleteSalesNote", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String deleteSalesNote(@RequestBody String salesNotes) {
        logger.info("SERVICE : deleteSalesNote");
        ResponseObject resObj = new ResponseObject();
        JsonObject salesNoteObj = new JsonObject();
        salesNoteObj = parser.parse(salesNotes).getAsJsonObject();
        Optional<Notes> oneNote = notesService.findOne(salesNoteObj.get("id").getAsString());
 		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 		List<User> user = userService.findByUserName(username,  PageRequest.of(0, 1)).getContent();
 		if(!user.get(0).getId().equals(oneNote.get().getUserId())) {
 			resObj.setStatus(ResponseObject.BAD_REQUEST);
 			resObj.setMsg("Başka Kullanıcıya Ait Notu Silemezsiniz");
 			logger.info("Başka Kullanıcıya Ait Notu Silemezsiniz");

 			return resObj.toString();
 		}
 		else {
 			notesService.delete(oneNote.get());
 			resObj.setStatus(ResponseObject.OK);
 			resObj.setMsg("Not Başarıyla Silindi");
 			logger.info("Not Başarıyla Silindi");
 		}
		return resObj.toString();
      }
	
//	@RequestMapping(value = "/editSalesNote", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
//    public String editSalesNote(@RequestBody String salesNotes) {
//        ResponseObject resObj = new ResponseObject();
//        JsonObject salesObj = new JsonObject();
//        salesObj = parser.parse(salesNotes).getAsJsonObject();
//        Optional<Sales> currentSales = salesService.findOne(salesObj.get("id").getAsString());
//        List<Notes> currentNotes = notesService.findBySalesId(salesObj.get("salesId").getAsString(), PageRequest.of(0, 10)).getContent();
//        JsonArray newNotes = new JsonArray();
//        newNotes = salesObj.get("notes").getAsJsonArray();
//        
//        if(currentSales.get().getNotes() !=null) {
//        	JsonArray currentNotes = new Gson().toJsonTree(currentSales.get().getNotes()).getAsJsonArray();
//     		if(!currentNotes.equals(newNotes)) {
//         		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//         		for (int i = 0; i < newNotes.size(); i++) {
//         			Notes control= new Gson().fromJson(newNotes.get(i).getAsJsonObject(), Notes.class);
//                 	if(!control.getUsername().equals(username)) {
//                 		if(!currentNotes.contains(newNotes.get(i))) {
//                 			resObj.setMsg("Başka kullanıcının oluşturduğu notu silemezsiniz");
//                 			resObj.setStatus(ResponseObject.BAD_REQUEST);
//                 			return resObj.toString();
//                 		}
//                 	}
//     			}
////         		if(newNotes.size()==0 && currentNotes.size()!=0)
////         		{
////         			List<Notes> test = new ArrayList<Notes>() ;
////         			for (int i = 0; i < currentNotes.size(); i++) {
////         				Notes control= new Gson().fromJson(currentNotes.get(i).getAsJsonObject(), Notes.class);
////         				if(control.getUsername().equals(username)) {
////                     		
////                     	}
////         			}
////         		}
//     		}
//        }
// 		List<Notes> test = new ArrayList<Notes>() ;
// 		for (int i = 0; i < newNotes.size(); i++) {
// 			Notes control= new Gson().fromJson(newNotes.get(i).getAsJsonObject(), Notes.class);
// 			test.add(control);
// 			}
//     		
//     	currentSales.get().setNotes(test);
// 		salesService.save(currentSales.get());
//    	resObj.setMsg("Satış Başarıyla Güncellendi");
//    	resObj.setStatus(ResponseObject.OK);
//		return resObj.toString();
//    }
	@RequestMapping(value = "/fetchSalesCustomers", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String fetchSalesCustomer() {
        ResponseObject resObj = new ResponseObject();
        JsonArray custArr = new JsonArray();
        Iterable<Customers> customers = customerService.findAll();
        for(Customers o : customers){
			 JsonObject temp = new JsonObject();
			 temp.addProperty("value", o.getId());
			 temp.addProperty("label", o.getCustomerShortName());
			 
			 custArr.add(temp);
	     }
        if(custArr.size()==0) {
            resObj.setStatus(ResponseObject.BAD_REQUEST);
            resObj.setMsg("Kayıtlı Müşteri Bulunamadı");
        }
        else {
        	resObj.setData(custArr);
            resObj.setStatus(ResponseObject.OK);
            resObj.setMsg("Bütün Müşteriler");
        }
        
		return resObj.toString();
    }
	
	@RequestMapping(value = "/fetchSales", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String fetchSales() {
        ResponseObject resObj = new ResponseObject();
        JsonArray resp = new JsonArray();
        for(Sales o : salesService.findAll()) {
        	if(!o.isDeletedSales()) {
        		JsonObject unitSales = new  JsonObject();
            	unitSales = o.toJson();
            	Optional<Customers> customer = customerService.findOne(o.getCustomerId());
            	unitSales.addProperty("customerShortName", customer.get().getCustomerShortName());
            	resp.add(unitSales);
        	}        	
        }
        //resObj.setData(QueryClass.fetchSales());
        resObj.setData(resp);
        resObj.setStatus(ResponseObject.OK);
        logger.info("SERVICE : fetchSales");
		return resObj.toString();
    }
	@RequestMapping(value = "/fetchSalesDetail", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String fetchSalesDetail(@RequestBody String salesId) throws InterruptedException, ExecutionException {
        ResponseObject resObj = new ResponseObject();
        JsonObject sales = new JsonObject();
		sales = parser.parse(salesId).getAsJsonObject();
		JsonObject resp = new JsonObject();
		Optional<Sales> temp = salesService.findOne(sales.get("id").getAsString());
		int noteCount = (int) QueryClass.salesNoteCount(sales.get("id").getAsString());
		Optional<Customers> customer = customerService.findOne(temp.get().getCustomerId());
		JsonObject salesObj = new JsonObject();
		salesObj = temp.get().toDetails();
		salesObj.addProperty("noteCount", noteCount);
		List<TreeObject> trees = treeObjectService.findBySalesId(sales.get("id").getAsString());
		JsonArray treeArray = new JsonArray();
		if(trees.size()!=0) {
			for(TreeObject treeObj : trees) {
				JsonObject treeObjWithMeasurement = new JsonObject();
				treeObjWithMeasurement=treeObj.toJsonForDetails();
				treeObjWithMeasurement.add("measurement", QueryClass.lastMeasurement(treeObj.getId()));
				treeArray.add(treeObjWithMeasurement);
			}
		}
		resp.add("soldTree", treeArray);
        resp.add("sales", salesObj);
        resp.add("customer", customer.get().toJson());
        //resObj.setData(QueryClass.fetchSales());
        resObj.addData(resp);
        resObj.setStatus(ResponseObject.OK);
        logger.info("SERVICE : fetchSales");
		return resObj.toString();
    }
	@RequestMapping(value = "/deleteSales", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String deleteSales(@RequestBody String salesId) {
		JsonObject sales = new JsonObject();
		sales = parser.parse(salesId).getAsJsonObject();
		LogsGenerate log = new SalesLogs(logsService);
		Optional<Sales> salesOpt= salesService.findOne(sales.get("id").getAsString());
		Sales salesObj = new Sales();
		salesObj = salesOpt.get();
		salesObj.setDeletedSales(true);
		salesObj.setCustomerId(null);
		salesService.save(salesObj);
		List<Notes> notes = notesService.findBySalesId(sales.get("id").getAsString(), PageRequest.of(0, 10)).getContent();
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(sales.has("reason")) {
        	log.infoDelete(username, sales.get("reason").getAsString());
        }
        for (Notes notesObj : notes) {
        	notesService.delete(notesObj);
		}
		ResponseObject resObj = new ResponseObject();
		log.infoDelete(username, "SATIS SILINDI SATIS KODU: "+salesOpt.get().getSalesCode());
        resObj.setMsg("Satış Başarıyla Silindi. Satış Kodu: "+salesOpt.get().getSalesCode());
        resObj.setStatus(ResponseObject.OK);
        logger.info("Satış Başarıyla Silindi. Satış Kodu: "+salesOpt.get().getSalesCode());
        
		return resObj.toString();
    }
	@RequestMapping(value = "/fetchAllUsers", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String fetchAllUsers() {
        ResponseObject resObj = new ResponseObject();	
        JsonArray users = new JsonArray();
        Iterable<User> userIter = userService.findAll();

        for(User o : userIter){
            JsonObject temp = new JsonObject();
            temp = o.toJson();
            users.add(temp);
        }
        resObj.setData(users);
        resObj.setStatus(ResponseObject.OK);
        logger.info("SERVICE : fetchAllUsers");
		return resObj.toString();
    }
	@RequestMapping(value = "/deleteUser", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String deleteUser(@RequestBody String userId) {
		JsonObject user = new JsonObject();
		user = parser.parse(userId).getAsJsonObject();
		Optional<User> userOpt= userService.findOne(user.get("id").getAsString());
		userService.delete(userOpt.get());
		ResponseObject resObj = new ResponseObject();
        resObj.setMsg("Kullanıcı Başarıyla Silindi");
        resObj.setStatus(ResponseObject.OK);
        logger.info("SERVICE : deleteUser");
        logger.info("Kullanıcı Başarıyla Silindi");
        
		return resObj.toString();
    }
	@RequestMapping(value = "/deleteTreeType", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String deleteTreeType(@RequestBody String treeObjId) {
		JsonObject tree = new JsonObject();
		tree = parser.parse(treeObjId).getAsJsonObject();
		Optional<Tree> treeOpt= treeService.findOne(tree.get("id").getAsString());
		treeService.delete(treeOpt.get());
		ResponseObject resObj = new ResponseObject();
        resObj.setMsg("Ağaç Türü Başarıyla Silindi");
        resObj.setStatus(ResponseObject.OK);
        logger.info("SERVICE : deleteTreeType");
        logger.info("Ağaç Türü Başarıyla Silindi");
        
		return resObj.toString();
    }
	
	@RequestMapping(value = "/addCustomer", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String addCustomer(@RequestBody Customers customer) {
		 ResponseObject resObj = new ResponseObject();		
			if(customer != null) {
				if(customer.getCustomerName() != null && !customer.getCustomerName().isEmpty()) {
					if(customer.getCustomerShortName() != null && !customer.getCustomerShortName().isEmpty()) {
						if(customer.getPhoneNum() != null && !customer.getPhoneNum().isEmpty()) {
							List<Customers> existingCustomer = customerService.findByCustomersName(customer.getCustomerName(), PageRequest.of(0, 1)).getContent();
							if(existingCustomer.size()>0) {
								resObj.setStatus(ResponseObject.BAD_REQUEST);
								resObj.setMsg("Bu Müşteri sistemde mevcut: "+customer.getCustomerName());
								logger.info("Müşteri sistemde mevcut: " + customer.getCustomerName());
								return resObj.toString();
							}
							else {
								customerService.save(customer);
								List<Customers> newCusto = customerService.findByCustomersName(customer.getCustomerName(), PageRequest.of(0, 1)).getContent();
								resObj.addData(newCusto.get(0).toJson());
								resObj.setStatus(ResponseObject.OK);
								resObj.setMsg("Müşteri Başarıyla Eklendi");
								logger.info("Müşteri Başarıyla Eklendi");
							}
						}else {
							resObj.setStatus(ResponseObject.BAD_REQUEST);
							resObj.setMsg("Lütfen Telefon Numarası bilgisini giriniz.");
							logger.info("Lütfen Telefon Numarası bilgisi eksik");
						}
							
					}else {
						resObj.setStatus(ResponseObject.BAD_REQUEST);
						resObj.setMsg("Lütfen Kısa ad bilgisini giriniz.");
						logger.info("Lütfen Kısa ad bilgisi eksik");
							}
						
				}
				else {
					resObj.setStatus(ResponseObject.BAD_REQUEST);
					resObj.setMsg("Lütfen Telefon Numarası bilgisini giriniz.");
					logger.info("Lütfen Kısa ad bilgisi eksik");
				}
			}
        
		return resObj.toString();
    }
	
	@RequestMapping(value = "/fetchCustomers", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String fetchCustomers(@RequestBody String request) {
		ResponseObject resObj = new ResponseObject();		
		JsonObject requestObj = new JsonObject();
		requestObj = parser.parse(request).getAsJsonObject();
		String param=requestObj.get("id").getAsString();
		JsonArray respArr = new JsonArray();
		if(!param.equals("allCustomers")) {
			resObj.addData(customerService.findOne(param).get().toJson());
			logger.info("fetchCustomer. Id : "+param);
			
		}
		else {
			 for(Customers o : customerService.findAll()){
				 
				 respArr.add(o.toJson());
		     }
			 resObj.setData(respArr);
			 logger.info("fetchCustomer All");
		}
		resObj.setStatus(ResponseObject.OK);
		return resObj.toString();
	}
	
	@RequestMapping(value = "/editCustomer", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String editCustomer(@RequestBody String customer) {
		ResponseObject resObj = new ResponseObject();		
		JsonObject requestObj = new JsonObject();
		requestObj = parser.parse(customer).getAsJsonObject();
		Optional<Customers> controlCustomer = customerService.findOne(requestObj.get("id").getAsString());
		if(controlCustomer.isPresent()) {
			Customers editCustomer = new Customers();
			editCustomer.setId(requestObj.get("id").getAsString());
			editCustomer.setCustomerShortName(requestObj.get("customerShortName").getAsString());
			editCustomer.setCustomerName(requestObj.get("customerName").getAsString());
			editCustomer.setPhoneNum(requestObj.get("phoneNum").getAsString());
			customerService.save(editCustomer);
			resObj.setStatus(ResponseObject.OK);
			logger.info("Müşteri Başarıyla Güncellendi");
			resObj.setMsg("Müşteri Başarıyla Güncellendi");
			return resObj.toString();
		}
		else {
			logger.info("Müşteri Bulunamadı.");
			resObj.setMsg("Müşteri Bulunamadı.");
			return resObj.toString();
		}
		
	}
	@RequestMapping(value = "/deleteCustomer", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String deleteCustomer(@RequestBody String customer) {
		ResponseObject resObj = new ResponseObject();		
		JsonObject requestObj = new JsonObject();
		requestObj = parser.parse(customer).getAsJsonObject();
		String id = requestObj.get("id").getAsString();
		List<Sales> salesList = salesService.findByCustomerId(id);
		System.out.println(salesList);
		if(salesList.size()==0) {
			customerService.delete(customerService.findOne(id).get());
			resObj.setStatus(ResponseObject.OK);
			logger.info("Müşteri Başarıyla Silindi");
			resObj.setMsg("Müşteri Başarıyla Silindi");
			return resObj.toString();

		}
		else {
			resObj.setStatus(ResponseObject.BAD_REQUEST);
			logger.info("Müşteri Bir Satışta Bulunduğu İçin Silinemez");
			resObj.setMsg("Müşteri Bir Satışta Bulunduğu İçin Silinemez");
			return resObj.toString();

		}
	}
	@RequestMapping(value = "/fetchCustomerSales", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String getCustomerSales(@RequestBody String customer) {
		ResponseObject resObj = new ResponseObject();		
		JsonObject requestObj = new JsonObject();
		requestObj = parser.parse(customer).getAsJsonObject();
		String id = requestObj.get("id").getAsString();
		List<Sales> salesList = salesService.findByCustomerId(id);
		System.out.println(salesList);
		if(salesList.size()==0) {
			resObj.setStatus(ResponseObject.OK);
			logger.info("Müşteriye Ait Satış Bulunamadı.");
			resObj.setMsg("Müşteriye Ait Satış Bulunamadı.");
			return resObj.toString();

		}
		else {
			JsonArray respArr = new JsonArray();
			for (Sales sales : salesList) {
				respArr.add(sales.toJson());
			}
			resObj.setData(respArr);
			resObj.setStatus(ResponseObject.OK);
			logger.info("Müşteri Ait Bütün Satışlar Getirildi.");
			resObj.setMsg("Müşteri Ait Bütün Satışlar Getirildi.");
			return resObj.toString();

		}
	}
	@RequestMapping(value = "/treeObjCount", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
    public String treeObjCount() {
		ResponseObject respObj = new ResponseObject();
		try {
			respObj.addData(QueryClass.treeCount());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			respObj.setMsg("Hiç Ağaç Bulunamadı");
			return respObj.toString();
		}
        return respObj.toString();
    }
	@RequestMapping(value = "/addTreeObjectToSale", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public String addTreeObjectToSale(@RequestBody String soldTreeObjString) {
		ResponseObject resObj = new ResponseObject();
		JsonObject soldTree = new JsonObject();
		soldTree = parser.parse(soldTreeObjString).getAsJsonObject();
		Optional<TreeObject> treeObject = treeObjectService.findOne(soldTree.get("id").getAsString());
		if(treeObject.get().isSalesStatus()) {
			resObj.setStatus(ResponseObject.BAD_REQUEST);
			resObj.setMsg("Ağaç Başka Bir Satışta");
			return resObj.toString();
		}
		treeObject.get().setSalesId(soldTree.get("salesId").getAsString());
		treeObject.get().setSalesStatus(true);
		treeObjectService.save(treeObject.get());
		resObj.setStatus(ResponseObject.OK);
		resObj.setMsg("Ağaç Satışa Başarıyla Eklendi");
		return resObj.toString();
	}
	@RequestMapping(value = "/addExpectedPayment", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public String addExpectedPayment(@RequestBody String expectedPaymentString) {
		ResponseObject resObj = new ResponseObject();
		JsonObject expectedObj = new JsonObject();
		JsonArray salesPlan = new JsonArray();
		expectedObj = parser.parse(expectedPaymentString).getAsJsonObject();
		String salesId= expectedObj.get("salesId").getAsString();
		salesPlan = expectedObj.getAsJsonArray("salesPlan").getAsJsonArray();
		if(salesPlan.size()==0) {
			resObj.setStatus(ResponseObject.BAD_REQUEST);
			resObj.setMsg("Satış objesi boş");
			return resObj.toString();
		}
		else {
			if(salesId!=null&&!salesId.isEmpty()) {
				Optional<Sales> sales = salesService.findOne(salesId);
				if(sales.isPresent()) {
					for(int i = 0; i<salesPlan.size();i++) {						
						SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("tr"));		 
						Date currentDate = new Date();
						ExpectedPayments expectedPayment = new ExpectedPayments();
						expectedPayment.setPaymentType(salesPlan.get(i).getAsJsonObject().get("paymentType").getAsString());
						expectedPayment.setSalesId(salesId);
						expectedPayment.setPredictedPaymentDate(salesPlan.get(i).getAsJsonObject().get("predictedPaymentDate").getAsString());
						expectedPayment.setPrice(salesPlan.get(i).getAsJsonObject().get("price").getAsInt());
						expectedPayment.setCreateDate(formatter.format(currentDate));
						expectedPaymentsService.save(expectedPayment);	
					}
				}else {
					resObj.setStatus(ResponseObject.BAD_REQUEST);
					resObj.setMsg("Satış Id Bulunamadı.");
					return resObj.toString();
				}
			}else {
				resObj.setStatus(ResponseObject.BAD_REQUEST);
				resObj.setMsg("Satış Id Boş Olamaz");
				return resObj.toString();
			}
			resObj.setStatus(ResponseObject.OK);
			resObj.setMsg("Beklenen Ödeme Başarıyla Tanımlandı");
			return resObj.toString();
		}
		
		
	}
	@RequestMapping(value = "/fetchExpectedPayments", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public String fetchExpectedPayments(@RequestBody String expectedPaymentSalesIdObj) {
		ResponseObject resObj = new ResponseObject();
		JsonObject salesIdObj = new JsonObject();
		JsonArray resArray = new  JsonArray();
		salesIdObj = parser.parse(expectedPaymentSalesIdObj).getAsJsonObject();
		List<ExpectedPayments> list = expectedPaymentsService.findBySalesId(salesIdObj.get("id").getAsString());
		if(list.size()==0) {
			resObj.setStatus(ResponseObject.BAD_REQUEST);
			resObj.setMsg("Beklenen Ödeme Bulunamadı.");
			return resObj.toString();
		}
		for(ExpectedPayments obj : list)
		{
			resArray.add(obj.toJson());
		}
		resObj.setData(resArray);
		resObj.setStatus(ResponseObject.OK);
		resObj.setMsg("Beklenen Ödemeler Getirildi");
		return resObj.toString();
	}
	@RequestMapping(value = "/addOccuredPayment", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public String addOccuredPayment(@RequestBody String occuredPayment) {
		JsonObject tempObj = new JsonObject();
		tempObj = parser.parse(occuredPayment).getAsJsonObject();
		OccuredPayments newOccuredPaymentObject = new OccuredPayments();
		ResponseObject resObj = new ResponseObject();
			if(tempObj.has("salesId")) {
				Optional<Sales> sales = salesService.findOne(tempObj.get("salesId").getAsString());
				if(sales.isPresent()) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", new Locale("tr"));		 
					Date currentDate = new Date();
					newOccuredPaymentObject.setSalesId(tempObj.get("salesId").getAsString());
					newOccuredPaymentObject.setNo(tempObj.get("no").getAsInt());
					newOccuredPaymentObject.setPaymentType(tempObj.get("paymentType").getAsString());
					newOccuredPaymentObject.setPrice(tempObj.get("price").getAsInt());
					newOccuredPaymentObject.setCreateDate(formatter.format(currentDate));
					occuredPaymentservice.save(newOccuredPaymentObject);
					Iterable<Qr> qr = qrService.findAll();
					Qr tempQr = new Qr();
					for (Qr qr2 : qr) {
						tempQr = qr2;
						tempQr.setOccuredNo(tempQr.getOccuredNo()+1);
					}
					qrService.save(tempQr);
				}else {
					resObj.setStatus(ResponseObject.BAD_REQUEST);
					resObj.setMsg("Satış Id Bulunamadı.");
					return resObj.toString();
				}
			}else {
				resObj.setStatus(ResponseObject.BAD_REQUEST);
				resObj.setMsg("Satış Id Boş Olamaz");
				return resObj.toString();
			}
			resObj.setStatus(ResponseObject.OK);
			resObj.setMsg("Gerçeklenen Ödeme Başarıyla Tanımlandı");
			return resObj.toString();
		
	}
	@RequestMapping(value = "/fetchOccuredPayments", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public String fetchOccuredPayments(@RequestBody String occuredPaymentSalesIdObj) {
		ResponseObject resObj = new ResponseObject();
		JsonObject salesIdObj = new JsonObject();
		JsonObject occuredResp = new JsonObject();
		JsonArray resArray = new  JsonArray();
		occuredResp.add("data", resArray);
		Iterable<Qr> qr = qrService.findAll();
		int occuredNo=0;
		for (Qr qr2 : qr) {
			occuredNo=qr2.getOccuredNo();
		}
		occuredResp.addProperty("occuredNo", occuredNo);
		salesIdObj = parser.parse(occuredPaymentSalesIdObj).getAsJsonObject();
		List<OccuredPayments> list = occuredPaymentservice.findBySalesId(salesIdObj.get("id").getAsString());
		if(list.size()==0) {
			resObj.setStatus(ResponseObject.OK);
			resObj.setMsg("Gerçekleşen Ödeme Bulunamadı.");
			resObj.addData(occuredResp);
			return resObj.toString();
		}
		for(OccuredPayments obj : list)
		{
			resArray.add(obj.toJson());
		}
		
		resObj.addData(occuredResp);
		resObj.setStatus(ResponseObject.OK);
		resObj.setMsg("Gerçekleşen Ödemeler Getirildi");
		return resObj.toString();
	}
	@RequestMapping(value = "/deleteOccuredPayment", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public String deleteOccuredPayment(@RequestBody String occuredPaymentIdObj) {
		ResponseObject resObj = new ResponseObject();
		JsonObject occuredObj = new JsonObject();
		occuredObj = parser.parse(occuredPaymentIdObj).getAsJsonObject();
		occuredPaymentservice.delete(occuredPaymentservice.findOne(occuredObj.get("id").getAsString()).get());
		resObj.setStatus(ResponseObject.OK);
		resObj.setMsg("Gerçekleşen Ödeme Başarıyla Silindi.");
		return resObj.toString();
	}
	@RequestMapping(value = "/removeTreeObjectToSale", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.POST)
	public String removeTreeObjectToSale(@RequestBody String removeSoldTreeObjString) {
		ResponseObject resObj = new ResponseObject();
		JsonObject soldTree = new JsonObject();
		soldTree = parser.parse(removeSoldTreeObjString).getAsJsonObject();
		Optional<TreeObject> treeObject = treeObjectService.findOne(soldTree.get("id").getAsString());
		treeObject.get().setSalesId(null);
		treeObject.get().setSalesStatus(false);
		treeObject.get().setPrice(0);
		treeObjectService.save(treeObject.get());
		resObj.setStatus(ResponseObject.OK);
		resObj.setMsg("Ağaç Satıştan Başarıyla Çıkarıldı");
		return resObj.toString();
	}
}
