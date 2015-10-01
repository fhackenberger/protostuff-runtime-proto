package net.webby.protostuff.runtime;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import io.protostuff.Schema;
import io.protostuff.parser.Field;
import io.protostuff.parser.Message;
import io.protostuff.parser.Proto;
import io.protostuff.parser.ProtoUtil;
import io.protostuff.parser.Field.Modifier;
import io.protostuff.runtime.RuntimeSchema;

/**
 * 
 * @author Alex Shvid
 *
 */


public class PrimitiveTest {

	@Test
	public void test() throws Exception {

		Schema<PrimitiveClass> schema = RuntimeSchema.getSchema(PrimitiveClass.class);
		
		String content = Generators.newProtoGenerator(schema).generate();
		
		System.out.println(content);
		
		Proto proto = new Proto(new File("test.proto"));
		ProtoUtil.loadFrom(new ByteArrayInputStream(content.getBytes()), proto);
		 
		String packageName = this.getClass().getPackage().getName();
		
		Assert.assertEquals(proto.getPackageName(), packageName.replace('.', '_'));
		Assert.assertEquals(proto.getJavaPackageName(), packageName);
		Assert.assertEquals(0, proto.getEnumGroups().size());
		Assert.assertEquals(1, proto.getMessages().size());
		
		Message msg = proto.getMessage("PrimitiveClass");
		Assert.assertNotNull(msg);
		
		for (Field<?> field : msg.getFieldMap().values()) {
			Assert.assertEquals(Modifier.OPTIONAL, field.getModifier());
		}
		
		Assert.assertEquals(8,  msg.getFieldCount());
		Assert.assertTrue(msg.getField("booleanValue") instanceof Field.Bool);
		Assert.assertTrue(msg.getField("byteValue") instanceof Field.UInt32);
		Assert.assertTrue(msg.getField("charValue") instanceof Field.UInt32);
		Assert.assertTrue(msg.getField("shortValue") instanceof Field.UInt32);
		Assert.assertTrue(msg.getField("intValue") instanceof Field.Int32);
		Assert.assertTrue(msg.getField("longValue") instanceof Field.Int64);
		Assert.assertTrue(msg.getField("floatValue") instanceof Field.Float);
		Assert.assertTrue(msg.getField("doubleValue") instanceof Field.Double);
	}
	
}
