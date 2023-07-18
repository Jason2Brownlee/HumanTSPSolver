class SolutionsController < ApplicationController  
  
  # GET /instances
  # GET /instances.xml
  def index    
    if(params[:problem_id] == nil)  
      # generic list all
      @instances = Instance.find(:all, :order => "start_date DESC")
    else
      # list by problem (nested route for example)
      @problem = Problem.find(params[:problem_id])
      @instances = @problem.instances.find(:all, :order => "start_date DESC")
    end
    
    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @instances }
    end
  end
  
  def current
    # retrieve the instance id
    @instance = Instance.current_instance    
    # must be a cleaner way
    redirect_to :action => "show", :id => @instance.id    
  end
  
  def show
    # retrieve the instance id
    @instance = Instance.find(params[:id])
    # respond
    respond_to do |format|
      format.html
      format.xml  { render :xml => @instance.histories }
    end
  end
  
end
