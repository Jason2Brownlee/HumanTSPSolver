class Admin::UploadController < ApplicationController
  
  before_filter :authenticate_admin
  
  def index
    # pass through 
  end
  
  def problem
    # pass through 
  end
  
  def optima
    # load existing probelms
    @problems = Problem.find(:all, :order => "name ASC")
    # pass through     
  end  
  
  
  def uploadProblem    
    # do the upload
    result = DataFile.saveProblem(params[:upload])
    # flash some things
    if(result == nil)
      flash[:notice] = 'TSPLIB file has been uploaded successfully.'
    else
      flash[:notice] = "Failed to upload file: #{result}, #{params[:upload]}"
    end
    # back to the problem page
    redirect_to :action => 'problem'
  end  
  
  
  
  def uploadOptima
    # get the problem
    @problem = Problem.find(params[:problem][:id])        
    # check of the problem already has optima
    if(@problem.has_optima?)
      flash[:notice] = 'Unable to upload optima, problem already has optimal tour defined.'   
    else      
      # upload the optima
      result = DataFile.saveOptima(params[:upload], @problem)    
      # assess
      if(result == nil)
        flash[:notice] = "TSPLIB Optima file for #{@problem.name} has been uploaded successfully." 
      else
        flash[:notice] = "Failed to upload file: #{result}"    
      end        
    end
      # back to the optima page
      redirect_to :action => 'optima'     
  end
  
end
