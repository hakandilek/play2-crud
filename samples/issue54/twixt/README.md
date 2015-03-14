Twixt is an alternative binding framework for Java Play apps.

Play documentation recommends making forms from model objects.  The model's validation annotations are used
during binding.  This works well in simple CRUD scenarios, but has limitations in larger applications:

   * the form may only have a subset of model fields.  Binding may fail due to missing @Required fields.
   * the same field may be used on multiple forms with different validation requirements.
   * a form may contain fields from multiple models.

A twixt is an object that sits between the model and the view.  Twixt fields have their own validation and formatting, separate
 from the model.  A typical edit action would get a model from the database, load it into a twixt, and render the twixt in a form.

      User user = User.find().getById(id); //get model
      UserTwixt twixt = new UserTwixt();   //create twixt
      twixt.copyFromModel(user);           //load twixt
      Form<UserTwixt> form = Form.form(UserTwixt.class).fill(twixt);
      //..render the form

copyFromModel copies fields automatically, or can be overridden to do custom copying. 

On post-back, use TwixtBinder to bind the HTTP request into your TwixtForm. copyToModel will
copy the validated data into the model.

      TwixtBinder<UsreTwixt> binder = new TwixtBinder();
      if (! binder.bind()) {
        Form<UserTwixt> form = Form.form(UserTwixt.class).fill(binder.get());
        //render the form again with validation errors and user's input data
      } else {
        UserTwixt twixt = binder.get();
        User user = User.find().getById(id);
        twixt.copyToModel(user);
        user.update();
      }

 Use Twixt value object as public fields in your twixt class.  A value class contains its own validation and formatting.  
 Twixt provides BooleanValue, IntegerValue, StringValue, ListValue, and others.
 Create your own classes to represent the types of data you need:

	  public class PhoneNumberValue extends StringValue
	  {
		 class PhoneValidator {
			public void validate(ValContext vtx, Value val) {
			 Pattern p = Pattern.compile("^(?=.{7,32}$)(\\(?\\+?[0-9]*\\)?)?[0-9_\\- \\(\\)]*((\\s?x\\s?|ext\\s?|extension\\s?)\\d{1,5}){0,1}$");  
			 String phone = val.toString();
			 boolean matchFound = p.matcher(phone).matches();
			 if (! matchFound) {
				vtx.addError("Not a valid phone number.");
			 }
			}
		 }

		 public PhoneNumberValue()
		 {
			super();
			setValidator(new PhoneValidator());
		 }
	  }
	  
Validation is code-based, not annotation-based.	  

## Controllers and Views
Use twixt objects in your controllers.  The view renders the value field in the normal way.
For example, if the twixt form had a StringValue field named <b>name</b>, render
it like this:

    @inputText(sampleForm("name"))

## Automatic CRUD Controllers with play-crud
Twixt integrates with play-crud (https://github.com/hakandilek/play2-crud) to provide automatic CRUD.  
This can be done in two ways.

### Dynamic CRUD 
The simplest approach is to create a controller based on DynamicTwixtController, telling it which model and twixt to use.  This controller 
standard CRUD actions (index, newForm, create, edit, update, show, and index), and views for each.
The model must derive from BasicModel.

### Custom Controller and View
To extend or customize, create a controller class based on TwixtController. It defines the same CRUD actions as DynamicTwixtController.
You must define the views for each.


   
   
   
   
   
   
