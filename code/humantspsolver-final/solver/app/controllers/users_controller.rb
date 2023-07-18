class UsersController < ApplicationController
  
  # actions of this controller that can only be accessed by logged in users
  before_filter :login_required, :only=>['profile', 'change_password']
    
  def signup
    @user = User.new(params[:user])
    if request.post?  
      if @user.save
        session[:user] = User.authenticate(@user.login, @user.password)
        flash[:message] = "Signup successful"
        redirect_to_home
      else
        flash[:warning] = "Signup unsuccessful"
      end
    end
  end
  
  def login
    if request.post?
      if session[:user] = User.authenticate(params[:user][:login], params[:user][:password])
        flash[:message]  = "Welcome back #{session[:user].login}"
        redirect_to_home
      else
        flash[:warning] = "Login unsuccessful"
      end
    end
  end
  
  def logout
    session[:user] = nil
    flash[:message] = 'Logged out'
    redirect_to_home
  end
  
  def forgot_password
    if request.post?
      u = User.find_by_email(params[:user][:email])
      if u
        if u.send_new_password
          flash[:message]  = "A new password has been sent by email."
          redirect_to :action=>'login'
        else
          flash[:warning]  = "Couldn't send password, please contact us."
        end
      else
        flash[:warning]  = "Couldn't send password."
      end
    end
  end
  
  def change_password
    @user=session[:user]
    if request.post?
      @user.update_attributes(:password=>params[:user][:password], :password_confirmation => params[:user][:password_confirmation])
      if @user.save
        flash[:message]="Password Changed"
      end
    end
  end
  
  def profile
    # pass through, must be logged in
    @user = session[:user]
  end
  
  def viewprofile
    # view another users profile
    @user = User.find(params[:id])
    
    # check if trying to view own profile
    session_user = session[:user]
    if(session_user != nil && session_user.id == @user.id)
      # redirect to real profile
      redirect_to :action=>'profile'
    end
  end
  
end
