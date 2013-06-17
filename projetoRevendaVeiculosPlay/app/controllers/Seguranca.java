package controllers;

import dao.DAO;
import dao.DAOFabricante;
import dao.DAOUsuario;
import play.mvc.*;
import play.mvc.Http.*;
public class Seguranca extends Security.Authenticator {
    private final DAOUsuario dao = (new DAOUsuario());
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }
    
    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Application.login());
    }
    
    public boolean isGerente(Context ctx){
    	final String login = ctx.session().get("login");
    	return dao.getPorLogin(login).isDefined();
    }
    
    
}