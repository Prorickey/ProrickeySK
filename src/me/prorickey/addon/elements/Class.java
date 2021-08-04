package me.prorickey.addon.elements;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import me.prorickey.addon.elements.data.GetRequestData;

public class Class {
	static {
        Classes.registerClass(new ClassInfo<>(GetRequestData.class, "getrequestdata")
                .user("getrequestdatas?")
                .parser(new Parser<GetRequestData>() {
 
                	@Override
                    public GetRequestData parse(String s, ParseContext context) {
                      return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                      return false;
                    }

                    @Override
                    public String toString(GetRequestData a, int flags) {
                      return a.toString();
                    }

                    @Override
                    public String toVariableNameString(GetRequestData a) {
                      return a.toString();
                    }

                    @Override
                    public String getVariableNamePattern() {
                      return ".*";
                    }

                })
                .serializer(new Serializer<GetRequestData>() {
                    @Override
                    public Fields serialize(GetRequestData a) throws NotSerializableException {
                      Fields fields = new Fields();
                      fields.putPrimitive("code", a.getCode());
                      fields.putObject("body", a.getBody());
                      return fields;
                    }

                    @Override
                    public void deserialize(GetRequestData a, Fields b) throws StreamCorruptedException,
                        NotSerializableException {
                      throw new UnsupportedOperationException();
                    }

                    @Override
                    protected GetRequestData deserialize(Fields a) throws StreamCorruptedException,
                        NotSerializableException {
                      return new GetRequestData(
                          a.getPrimitive("code", int.class),
                          a.getObject("body", String.class)
                      );
                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                      return false;
                    }

                    @Override
                    public boolean canBeInstantiated() {
                      return false;
                    }

                  }));
          
	}
}
