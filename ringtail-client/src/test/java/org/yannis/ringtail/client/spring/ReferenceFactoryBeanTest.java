package org.yannis.ringtail.client.spring;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yannis.ringtail.client.spring.data.Animal;

/**
 * Created by dell on 2016/7/6.
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:consumer.xml"
})*/
public class ReferenceFactoryBeanTest {

    @Autowired
    private Animal animal;

    ClassPathXmlApplicationContext context;

    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(new String[] {"/consumer.xml"});
        context.start();
    }

    @Test
    public void invoke(){
        Animal animal = context.getBean(Animal.class);
        System.out.println(animal.speek(2, "wauwaauooo"));
    }

}