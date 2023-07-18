# Filters added to this controller apply to all controllers in the application.
# Likewise, all the methods added will be available for all controllers.

class ApplicationController < ActionController::Base
  helper :all # include all helpers, all the time
  
  # See ActionController::RequestForgeryProtection for details
  # Uncomment the :secret if you're not using the cookie session store
  protect_from_forgery # :secret => '02778cabf1caaeba7fc996574e706e93'
  
  # prevent anything with password in it from being logged
  filter_parameter_logging "password"
  
  #
  # a filter that allows us to control access to actions
  #
  def login_required
    if session[:user]
      return true
    end
    flash[:warning]='Please login to continue'
    session[:return_to]=request.request_uri
    redirect_to :controller => "users", :action => "login"
    return false 
  end
  
  def current_user
    session[:user]
  end
  
  def has_current_user
    return current_user != nil
  end

  def authenticate_admin
    authenticate_or_request_with_http_basic do |name, pass|
      authenticate_admin_test(name, pass)      
    end
  end
  
  def authenticate_admin_test(name, pass)
    # must be logged in
    if(has_current_user)
      if(current_user.login == 'jasonb')
        # re-authenticate
        if(User.authenticate(name, pass))
          return true
        end
      end
    end    
    return false
  end
  
  #
  # used to redirect to a page stored in the session 
  # (It redirects to the url stored in the variable session[:return_to])
  #
  def redirect_to_stored
    if return_to = session[:return_to]
      session[:return_to]=nil
      redirect_to_url(return_to)
    else
      redirect_to_home
    end
  end  
  
  def redirect_to_home
    redirect_to :controller => 'home'
  end
  
end
