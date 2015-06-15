package tests;

import javax.swing.text.StyledEditorKit.ForegroundAction;
import Expressions.*;

public class test {

	
	
	public static double getsimplyR(int j,int k) {
		if (k==1){
			ExpressionA t=new ExpressionA();
		for (int i = 1; i <=j ; i++) {
			t.Submeet_In_Expression(i);
		}
		return t.GetValue();
		}
		else if (k==2){
			ExpressionC t1=new ExpressionC();
			ExpressionB t=new ExpressionB();
			for (int i = 1; i <=j ; i++) {
				t.Submeet_In_Expression(i);
				t1.Submeet_In_Expression(i);
			}
			//System.out.println(t.GetValue()+"+"+t1.GetValue());
			return t.GetValue()+t1.GetValue();
		}
		return -1;
	}
}
