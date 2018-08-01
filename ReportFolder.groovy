import jenkins.model.*;
import hudson.model.*;
def folderName = "TestRun_2018-07-20_15-54-13-PM";
def pa = new ParametersAction([
new StringParameterValue("FOLDERNAME_QA_JOB", folderName)
])
Thread.currentThread().executable.addAction(pa);