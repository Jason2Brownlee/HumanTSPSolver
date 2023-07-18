class OptimalToursController < ApplicationController

  before_filter :load_problem
  
  def load_problem
    @problem = Problem.find(params[:problem_id])
  end  
  
  # GET /optimal_tours
  # GET /optimal_tours.xml
  def index
    @optimal_tours = @problem.optimal_tours.find(:all)

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @optimal_tours }
    end
  end
  
end
