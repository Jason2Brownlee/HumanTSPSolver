class CoordinatesController < ApplicationController
  
  before_filter :load_problem
  
  def load_problem
    @problem = Problem.find(params[:problem_id])
  end  
  
  # GET /coordinates
  # GET /coordinates.xml
  def index
    @coordinates = @problem.coordinates.find(:all)

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @coordinates }
    end
  end

end
