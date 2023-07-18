class ProblemsController < ApplicationController
  # GET /problems
  # GET /problems.xml
  def index
    @problems = Problem.find(:all, :order => "num_cities ASC")
    
    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @problems }
    end
  end
  
  # GET /problems/1
  # GET /problems/1.xml
  def show
    # retrieve the solution
    @problem = Problem.find(params[:id])    
    
    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @problem }
    end
  end
  
  def current
    # retrieve the instance id
    @instance = Instance.current_instance    
    # must be a cleaner way
    redirect_to :action => "show", :id => @instance.problem
  end
  
end
