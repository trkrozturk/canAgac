package com.can.tree;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication(scanBasePackages={
		"com.can.tree"})
public class TreeApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(TreeApplication.class, args);
	}
	
//	 @Autowired
//	 private UserService userService;
//	 @Autowired
//	 private QrService qrService;
//	 @Autowired
//	 private TreeService treeService;
	 @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	 
	 
	@Override
    public void run(String... args) throws Exception {
		System.out.println("API RUNNING");
        //fuzzey search
//        Page<User> Users = userService.findByUserName("Apache Lucene Basics", new PageRequest(0, 10));
//        Iterable<User> test = userService.findAll();
////        Qr rr = new Qr();
////        rr.setLastQrNumber("100000000000");
////        //List<Book> books = bookService.findByTitle("Elasticsearch Basics");
////        qrService.save(rr);
//        for(Object o : test){
//            System.out.println(o.toString());
//        }
        //Users.forEach(x -> System.out.println(x));


    }
}
