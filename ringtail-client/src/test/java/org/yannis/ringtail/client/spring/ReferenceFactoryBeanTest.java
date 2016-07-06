package org.yannis.ringtail.client.spring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yannis.ringtail.client.spring.data.Animal;

import static org.junit.Assert.*;

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

    @Before
    public void init(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/consumer.xml"});
        context.start();
    }

    @Test
    public void invoke(){
        System.out.println(animal.speek(2, "wauwaauooo"));
    }

}