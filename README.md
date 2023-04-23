# App Wish Project






### What is app wish?
<p>Have you ever wished that you could create an app by just writing:  <i> Make this app</i> ?  </p>
Well, now you can!


### How does it work
<p>App Wish makes requests to an Open AI model (ChatGPT) and builds a fully functional app for you with the responses it receives. It will try to compile the code received and if it fails it retries again until a successful compilation is achieved .</p>



#### How to start the project:
1.	Add your OPENAI API key as an environment variable:
      ```export OPENAI_TOKEN=YOUR_KEY```
2. Run this command:
   ``` Make run ```
3. Make a wish :)



#### Generated app folder path
The generated apps will be saved in:
```AppWish/AppWish/src/main/resources/java_source_code_classes_tmp```
It's possible to run applications directly from app wish given there is no input parameters. If so, please run the class file manually with the required input parameters.


#### App Wish Generation Tips
1.	If you desire a window app, add “with gui” to your text. \
      <b>Example:</b>  ```mp3 player with gui```
2.	If you desire user input to your app, add “with user input to your text”. \
      <b>Example:</b>  ```calculator with gui “and user input”```


#### System Requirements
1.	Linux / OSX
2.	Java
3.	MVN
4.  Make



##### Credentials requirements  <u>(Needed for API Key)</u>

1. An account (free or paid) at OpenAI https://auth0.openai.com/u/signup/




#### Known issues and problem-solving
1. Depending on the level of the app wish it may take more or less time to generate the app. Disable the annoying waiting dialog in gnome by adding more times before it shows:
   ```gsettings set org.gnome.mutter check-alive-timeout 60000```
2. Make sure that all files in ```AppWish/AppWish/src/main/resources/sh_scripts``` are executable (<B>Can be done with sudo chmod +x at that path</B>).


## Include external jars in your classpath (not required)
If your app wishes receive code with non-existing libraries you can either add those externally by downloading jars from https://mvnrepository.com/ and add them to you class path.
The easiest way to do that is to add a folder for them and then export an environment variable pointing to it.
``` export CLASSPATH=$CLASSPATH:/path/to/jar_folder```




