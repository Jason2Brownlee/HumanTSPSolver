class Feedback
   include Validatable
   
   attr_accessor :name, :email, :msg
  
   # all attributes are required
   validates_presence_of :name, :email, :message => "Details Required."   
   validates_presence_of :msg, :message => "Message Required."
   
   # valid email address
   validates_format_of :email, :with => /^([^@\s]+)@((?:[-a-z0-9]+\.)+[a-z]{2,})$/i, :message => "Invalid email address"  
  
  def provide_feedback_message
    if(valid?)
      Notifications.deliver_provide_feedback(self)
    else
      false
    end
  end
  
end