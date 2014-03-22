package org.deeplearning4j.example.mnist;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.deeplearning4j.datasets.DataSet;
import org.deeplearning4j.datasets.iterator.DataSetIterator;
import org.deeplearning4j.datasets.iterator.impl.RawMnistDataSetIterator;
import org.deeplearning4j.dbn.DBN;
import org.deeplearning4j.util.SerializationUtils;

public class RawDBNFinetuneExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		DataSetIterator iter = new RawMnistDataSetIterator(80,60000);
		DBN d = new DBN.Builder().buildEmpty();
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(args[0])));
		d.load(bis);
		bis.close();
		while(iter.hasNext()) {
			DataSet d2 = iter.next();
			d.setInput(d2.getFirst());
			d.finetune(d2.getSecond(), 0.0001, 1000);
		}
		
		SerializationUtils.saveObject(d, new File(args[1]));
		
		
	}

}