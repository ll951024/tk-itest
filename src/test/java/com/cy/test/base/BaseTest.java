package com.cy.test.base;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Listeners;

//@Listeners({ CyTestNGListener.class })
@ContextConfiguration(locations = { "classpath*:application.xml" })
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

}
