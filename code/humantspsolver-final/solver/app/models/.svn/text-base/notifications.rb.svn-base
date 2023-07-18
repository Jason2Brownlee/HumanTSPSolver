class Notifications < ActionMailer::Base
 
  def provide_feedback(feedback)
    @subject    = "[humanTSPsolver.com] User Feedback"
    @body['name'] = feedback.name
    @body['email'] = feedback.email
    @body['msg'] = feedback.msg
    @recipients = "feedback@humanTSPsolver.com"
    @from       = "donotreply@humanTSPsolver.com"
    @sent_on    = Time.now
    @headers    = {}
  end
  
  def forgot_password(to, login, pass, sent_at = Time.now)
    @subject    = "[humanTSPsolver.com] Your password is ..."
    @body['login']=login
    @body['pass']=pass
    @recipients = to
    @from       = "donotreply@humanTSPsolver.com"
    @sent_on    = sent_at
    @headers    = {}
  end
  
end
