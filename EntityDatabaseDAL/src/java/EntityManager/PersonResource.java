/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EntityDB;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.json.JSONObject;


/**
 * REST Web Service
 *
 * @author ramyabalaji
 */

@Path("entity")
public class PersonResource {
    @Context
    private UriInfo context;

    /** Creates a new instance of PersonResource */
    public PersonResource() {
    }

    /**
     * Retrieves representation of an instance of EntityDB.PersonResource
     * @return an instance of EntityDB.Person
     */
    @Path("person/retrieve")
    @GET     @Produces("application/json")
    public String retrievePerson(@QueryParam ("Email") String PersonEmail) {
        //TODO return proper representation object
        JSONObject json= new JSONObject();
        try
        {
            JSONObject content=new JSONObject(PersonEmail);
            Person person=Person.selectByPersonEmail(content.getString("Email"));
            assert(person!=null);
           json.put("Email", person.getEmail());
           json.put("Phone", person.getPhone());
           json.put("FirstName",person.getFirstName());
           json.put("LastName", person.getLastName());
           return json.toString();
        } catch (Exception ex){
            return (ex.toString());
           // return json;
        }                    
}

    /** POST method to update fields. Email is not updateable**/
    @Path("person/update")
    @POST @Consumes("application/json")
    public String updatePerson(String personInfo)
    {
        try
        {
            JSONObject content=new JSONObject(personInfo);
            String firstName=content.getString("FirstName");
            String lastName=content.getString("LastName");
            String phone=content.getString("Phone");
            Person person=Person.selectByPersonEmail(content.getString("Email"));
            if  (firstName.length() !=0)
               person.setFirstName(firstName);
            if  (lastName.length() !=0)
               person.setLastName(lastName);
            if  (phone.length() >9)
               person.setPhone(phone);
            person.save();
            return ("Successfully Updated People- Non system User");

        }
        catch (Exception E)
        {
             return (E.toString());
        }
    }
   

    
    @Path("person/delete")
    @DELETE
    public String deletePerson(@QueryParam ("Email") String PersonEmail){
    {
      String EML=null;
      try {
          JSONObject content = new JSONObject(PersonEmail);
          EML=content.toString();
          System.out.println("Email is "+content.getString("Email")) ;
          Person person = Person.selectByPersonEmail(content.getString("Email"));
           person.delete(true);
           return ("Person Delete Successful");
        } catch (Exception E){
           return (EML);
        }
     }
    }


    
    
    
    /**
     * PUT method for reating an instance of PersonResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @Path("person/create")
    @PUT
    @Consumes("application/json")
    public String createPerson(String json) {
      String personEmail  =null;
      try {
          JSONObject content = new JSONObject(json);
          Person person = new Person();
          person.createNewID();
          String strEmail = (String) content.get("Email");
          person.setEmail(strEmail);
          person.setFirstName((String) content.get("FirstName"));
          person.setLastName((String) content.get("LastName"));
          person.setPhone((String) content.get("Phone"));
          person.save();
          personEmail= (String) content.get("Email");
       } catch (Exception E){
             return E.toString();
       }
       return  "Successfully Added Non System User(Person): " + personEmail;
    }


    }

