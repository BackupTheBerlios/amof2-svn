/*
 * Generated by XDoclet - Do not edit!
 */
package hub.sam.mof.reflection.server.ejb;

/**
 * Home interface for ServerRepository.
 */
public interface ServerRepositoryHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/ServerRepository";
   public static final String JNDI_NAME="ejb/ServerRepository";

   public hub.sam.mof.reflection.server.ejb.ServerRepository create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}
