class HomeController < ApplicationController
  
  # index
  def index
    # nothing
  end
  
  
  # static home content (see the route for handing the page parameter)
  # list the pages in the routes configuration
  def show    
    render :action => params[:page]       
  end  
  
  def feedback
    @feedback = Feedback.new
    if request.post?                  
      @feedback.name = params[:feedback][:name]
      @feedback.email = params[:feedback][:email]
      @feedback.msg = params[:feedback][:msg]

      if(@feedback.valid?)        
        @feedback.provide_feedback_message
        flash[:message]  = "Thank you for your feedback."    
        redirect_to :action => 'index' 
      else
        flash[:message]  = "Please check your feedback details."    
      end      
    end            
  end
  
end
