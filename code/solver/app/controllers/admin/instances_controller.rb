class Admin::InstancesController < ApplicationController
  
  before_filter :authenticate_admin
  
  # GET /instances
  # GET /instances.xml
  def index
    @instances = Instance.find(:all, :order => "start_date ASC")

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @instances }
    end
  end

  # GET /instances/1
  # GET /instances/1.xml
  def show
    @instance = Instance.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @instance }
    end
  end

  # GET /instances/new
  # GET /instances/new.xml
  def new
    @problems = Problem.find(:all, :order => "name ASC")
    @instance = Instance.new
    @instance.start_date = DateTime.now

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @instance }
    end
  end

  # GET /instances/1/edit
#  def edit
#    @instance = Instance.find(params[:id])
#  end

  # POST /instances
  # POST /instances.xml
  def create    
    @instance = Instance.new(params[:instance])    

    if @instance.save
      flash[:notice] = 'Instance was successfully created.'
      redirect_to :action => "index"
    else
      render :action => "new"
    end
  end

  # PUT /instances/1
  # PUT /instances/1.xml
#  def update
#    @instance = Instance.find(params[:id])
#
#    respond_to do |format|
#      if @instance.update_attributes(params[:instance])
#        flash[:notice] = 'Instance was successfully updated.'
#        format.html { redirect_to(@instance) }
#        format.xml  { head :ok }
#      else
#        format.html { render :action => "edit" }
#        format.xml  { render :xml => @instance.errors, :status => :unprocessable_entity }
#      end
#    end
#  end

  # DELETE /instances/1
  # DELETE /instances/1.xml
#  def destroy
#    @instance = Instance.find(params[:id])
#    @instance.destroy
#
#    respond_to do |format|
#      format.html { redirect_to(instances_url) }
#      format.xml  { head :ok }
#    end
#  end  
  
end
