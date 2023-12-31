package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHtttpHelper;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;


//@Configuration
public class TwitterCLIBean {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(TwitterCLIBean.class);
        TwitterCLIApp app=context.getBean(TwitterCLIApp.class);
        app.run(args);
    }
    @Bean
    public TwitterCLIApp twitterCLIApp(Controller controller){
        return new TwitterCLIApp(controller);
    }
    @Bean
    public Controller controller(Service service){
        return new TwitterController(service);
    }
    @Bean
    public Service service(CrdDao dao){
        return new TwitterService(dao);
    }
    @Bean
    public CrdDao crdDao(HttpHelper httpHelper){
        return new TwitterDao(httpHelper);
    }
    @Bean
    HttpHelper httpHelper(){
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");
        return new TwitterHtttpHelper(CONSUMER_KEY,CONSUMER_SECRET,ACCESS_TOKEN,TOKEN_SECRET);

    }

}
