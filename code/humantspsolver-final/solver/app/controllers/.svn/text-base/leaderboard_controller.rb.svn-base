class LeaderboardController < ApplicationController
  
  def index
    # retrieve the top 50
    @users = User.users_best_ever(25)
    
    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @users }
    end  
  end
  
end
