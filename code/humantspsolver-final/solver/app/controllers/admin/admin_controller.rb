class Admin::AdminController < ApplicationController
  before_filter :authenticate_admin


  
  def index
    # pass through 
  end
end
