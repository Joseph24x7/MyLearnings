package com.mylearnings;

import com.mylearnings.java.spring.MyPrototypeBean;
import com.mylearnings.java.spring.MySingletonBean;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@RequiredArgsConstructor
public class MyLearningApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(MyLearningApplication.class, args);

		// Retrieve two instances of MyPrototypeBean
		MyPrototypeBean bean1 = context.getBean(MyPrototypeBean.class);
		MyPrototypeBean bean2 = context.getBean(MyPrototypeBean.class);

		MySingletonBean singletonBean = context.getBean(MySingletonBean.class);
		singletonBean.doSomething();

	}

}
