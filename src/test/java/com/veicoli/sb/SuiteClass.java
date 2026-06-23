package com.veicoli.sb;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.veicoli.sb.bici.BiciTest;
import com.veicoli.sb.macchina.MacchinaTest;
import com.veicoli.sb.moto.MotoTest;
import com.veicoli.sb.veicoli.VeicoliTest;

@Suite
@SelectClasses({
	VeicoliTest.class,
	MacchinaTest.class,
	MotoTest.class,
	BiciTest.class
})
public class SuiteClass {

}
