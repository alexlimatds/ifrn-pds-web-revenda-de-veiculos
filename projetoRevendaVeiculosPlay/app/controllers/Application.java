package controllers;

//import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render());
	}

	public static class Login {
		public String login;
		public String senha;

		public String validate() {
			if ("admin".equals(login) && "123".equals(senha)) {
				return null;
			}
			return "Senha inválida";
		}
	}

	private static Form<Login> formLogin = new Form<Login>(Login.class);

	public static Result login() {
		return ok(login.render(formLogin));
	}

	public static Result autenticar() {
		Form<Login> f = formLogin.bindFromRequest();
		if (f.hasErrors()) {
			flash("error", "senha inválida");
			return badRequest(login.render(f));
		} else {
			session("login", f.get().login);
			return redirect(routes.Application.index());
		}
	}

	public static Result logout() {
		session().clear();
		return redirect(routes.Application.login());
	}
}