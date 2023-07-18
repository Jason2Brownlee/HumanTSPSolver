# Methods added to this helper will be available to all templates in the application.
module ApplicationHelper
   
  def current_user
    return session[:user]
  end
  
  def is_current_user?
    return (current_user == nil)
  end
  
  def applet_size
    size = cookies[:applet_size]
    
    if(size == nil)
      cookies[:applet_size] = "small"
      return 400
    elsif(size.index("small") != nil)
      return 400
    elsif size.index("medium") != nil
      return 550
    elsif size.index("large") != nil
      return 700
    end
    
  end
  
end
