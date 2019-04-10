package katherina.galustyan.testtask.irens.palindrome.config;

import katherina.galustyan.testtask.irens.palindrome.searcher.PalindromeSearcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

/**
 * Created by kate on 06.04.2019.
 */
@Configuration
public class BeanConfig {

    @Bean
    public PalindromeSearcher palindromeSearcher(){
        return new PalindromeSearcher();
    }
}
