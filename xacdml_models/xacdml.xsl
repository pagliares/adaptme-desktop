<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
version="1.0"
>
<xsl:output method="text"/>
<xsl:template match="/">
<xsl:text>

//
//Automatically generated by XACDML
//
package adaptme;
import java.util.HashMap;
import java.util.Iterator;
import simula.manager.*;
</xsl:text>
<xsl:apply-templates/>
<xsl:text>
 // System.out.println("\nThat's all folks!");
 }
}</xsl:text>
</xsl:template>

<!-- <xsl:template match="acd">
<xsl:text>public class </xsl:text>
<xsl:value-of select="@id"/> -->
<xsl:template match="acd">
public class DynamicExperimentationProgramProxy implements IDynamicExperimentationProgramProxy
<xsl:text>
{

 private SimulationManager man;
 private float simulationDuration;
 
 public DynamicExperimentationProgramProxy() {
   this.man = new SimulationManager();
 }
 
 public void setSimulationManager(SimulationManager simulationManager) {
   this.man = simulationManager;
 }
 
 public SimulationManager getSimulationManager() {
   return man;
 }
 
 public void setSimulationDuration(float simulationDuration) {
   this.simulationDuration = simulationDuration;
 }
 
 public float getSimulationDuration() {
   return simulationDuration;
 }
 
 public void execute(float simulationTime) {
  // System.out.println("\nGeracao de </xsl:text>
  // <xsl:value-of select="@id"/>
  // <xsl:text> atraves de Simulation Manager");
  // System.out.println("\n\t\t\tSIMULATION RESULTS AS IMPLEMENTED BY WLADIMIR\n");
  // System.out.println("ACD activiites created");
  
  QueueEntry qe;//queues 
  ResourceEntry re;//resource queues
  ExternalActiveEntry eae;//generator and detroy
  InternalActiveEntry iae;//activivity and router
  InterruptActiveEntry intae;//interrupts
  ObserverEntry oe;
  HistogramEntry he;

  //queues and observer queues
  </xsl:text>

  <xsl:apply-templates select="dead"/>

  <xsl:text>
  //active states

   //externals (generates and destroys)
  </xsl:text>

  <xsl:apply-templates select="generate"/>

  <xsl:apply-templates select="destroy"/>

  <xsl:text>

   //internals (activities and routers)

  </xsl:text>

  <xsl:apply-templates select="act"/>

  <xsl:apply-templates select="router"/>


  <xsl:text>

  //observers

  </xsl:text>

  <xsl:call-template name="dead_obs"/>
  <xsl:call-template name="gen_obs"/>
  <xsl:call-template name="act_obs"/>

  //generate the model

  if (man.GenerateModel())
  //   System.out.println("\nModel successfuly generated!\n");
  //else
  //{
  //   System.out.println("\nThere was an errors during the model generation!\n");
  //   System.out.println("Exiting...\n");
  //   return;
  //}

  //start the simulation

  <!-- System.out.println("Starting the simulation. Simulation wil run unitl time=</xsl:text>
  <xsl:variable name="time">
  <xsl:choose>
  <xsl:when test="simtime">
  <xsl:value-of select="simtime/@time"/>
  </xsl:when>
  <xsl:otherwise>
  <xsl:value-of select="500"/>
  </xsl:otherwise>
  </xsl:choose>
   <xsl:value-of select="$time"/>");
  </xsl:variable> -->
  
   //System.out.println("Starting the simulation. Each simulation replication (execution) wil run unitl time=" + simulationDuration + 
    //		" equivalent to " + simulationDuration/480 + " days\n");
  

 
   <!--
  Se tiver iteration and release, chama de um jeito, se tiver so release, de outro, so iteration, de outro, nenhum, de outro
   <xsl:choose>
  <xsl:when test="/acd/act[@id='Iteration'] and /acd/act[@id='Iteration']">//iteration e release
   man.ExecuteSimulation(<xsl:text><xsl:value-of select="$time"/></xsl:text>,(float)<xsl:text><xsl:if test="/acd/act[@id='Iteration']"><xsl:value-of select="act[@id='Iteration']/stat/@parm1" /></xsl:if></xsl:text>, (float)<xsl:text><xsl:if test="/acd/act[@id='Release']"><xsl:value-of select="act[@id='Release']/stat/@parm1" /></xsl:if> </xsl:text>);  
  </xsl:when> 
  <xsl:when test="/acd/act[@id='Iteration'] and not(/acd/act[@id='Iteration'])">//so iteration
  man.ExecuteSimulation(<xsl:text><xsl:value-of select="$time"/></xsl:text>, (float)<xsl:text><xsl:if test="/acd/act[@id='Iteration']"><xsl:value-of select="act[@id='Iteration']/stat/@parm1" /></xsl:if> </xsl:text>, (float)<xsl:text><xsl:if test="/acd/act[@id='Release']"><xsl:value-of select="act[@id='Release']/stat/@parm1" /></xsl:if> </xsl:text>);  
  </xsl:when> 
   <xsl:when test="not(/acd/act[@id='Iteration']) and /acd/act[@id='Iteration']">//so release
    man.ExecuteSimulation(<xsl:text><xsl:value-of select="$time"/></xsl:text>, 0 , (float)<xsl:text><xsl:if test="/acd/act[@id='Release']"><xsl:value-of select="act[@id='Release']/stat/@parm1" /></xsl:if> </xsl:text>);  
     </xsl:when>
     <xsl:when test="not(/acd/act[@id='Iteration']) and not(/acd/act[@id='Iteration'])">//nenhum nem outro
    man.ExecuteSimulation(<xsl:text><xsl:value-of select="$time"/></xsl:text>);  
     </xsl:when> 
   
 </xsl:choose>  -->
  
 
  <!-- Se tiver iteration and release, chama de um jeito, se tiver so release, de outro, so iteration, de outro, nenhum, de outro -->
   <xsl:choose>
  <xsl:when test="/acd/act[@id='Iteration'] and /acd/act[@id='Iteration']">//iteration e release
   man.ExecuteSimulation(simulationDuration,(float)<xsl:text><xsl:if test="/acd/act[@id='Iteration']"><xsl:value-of select="act[@id='Iteration']/stat/@parm1" /></xsl:if></xsl:text>, (float)<xsl:text><xsl:if test="/acd/act[@id='Release']"><xsl:value-of select="act[@id='Release']/stat/@parm1" /></xsl:if> </xsl:text>);  
  </xsl:when> 
  <xsl:when test="/acd/act[@id='Iteration'] and not(/acd/act[@id='Iteration'])">//so iteration
  man.ExecuteSimulation(simulationDuration, (float)<xsl:text><xsl:if test="/acd/act[@id='Iteration']"><xsl:value-of select="act[@id='Iteration']/stat/@parm1" /></xsl:if> </xsl:text>, (float)<xsl:text><xsl:if test="/acd/act[@id='Release']"><xsl:value-of select="act[@id='Release']/stat/@parm1" /></xsl:if> </xsl:text>);  
  </xsl:when> 
   <xsl:when test="not(/acd/act[@id='Iteration']) and /acd/act[@id='Iteration']">//so release
    man.ExecuteSimulation(simulationDuration, 0 , (float)<xsl:text><xsl:if test="/acd/act[@id='Release']"><xsl:value-of select="act[@id='Release']/stat/@parm1" /></xsl:if> </xsl:text>);  
     </xsl:when>
     <xsl:when test="not(/acd/act[@id='Iteration']) and not(/acd/act[@id='Iteration'])">//nenhum nem outro
    man.ExecuteSimulation(simulationDuration);  
     </xsl:when> 
   
 </xsl:choose> 
  
  
  while (!man.Finished())
  {
   try
   {
    Thread.sleep(1000);
   }
   catch(InterruptedException e) {break;}
  }

  //System.out.println("Simulation Stopped!\n");

  //finally, output the results
  //man.OutputSimulationResults (" <xsl:value-of select="@id"/><xsl:text>.out");</xsl:text>

</xsl:template>

<xsl:template match="dead">
  <xsl:if test="type/@size != type/@init" >
  <xsl:text>
  qe = new QueueEntry();
  qe.SetId("</xsl:text><xsl:value-of select="@id"/><xsl:text>");//mapped by dead id</xsl:text>
  qe.initialQuantity = <xsl:value-of select="type/@init"/>;
  qe.setMax((short)<xsl:value-of select="type/@size"/><xsl:text>);
  </xsl:text>
   
  <xsl:apply-templates select="observer"/>
  <xsl:text>man.AddQueue(qe);
  </xsl:text>
  </xsl:if>
  <xsl:if test="type/@size = type/@init" >
  <xsl:text>
  re = new ResourceEntry();
  re.SetId("</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");//mapped by dead id
  re.setInit((short) </xsl:text>
  <xsl:value-of select="type/@init"/>
  <xsl:text>);
  
  </xsl:text>
  <xsl:apply-templates select="observer"/>
  <xsl:text>man.AddResource(re);</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="dead/observer">
  <xsl:if test="../type/@size = ../type/@init" >
  <xsl:text>re.setObsid("</xsl:text>
  </xsl:if>
  <xsl:if test="../type/@size != ../type/@init" >
  <xsl:text>qe.setObsid("</xsl:text>
  </xsl:if>
  <xsl:value-of select="@name"/>
  <xsl:text>");//observer
  </xsl:text>
</xsl:template>

<xsl:template match="generate">
  <xsl:text>
  eae = new ExternalActiveEntry(true); //generate
  eae.SetId("</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>"); //mapped by generate id
  eae.setQID("</xsl:text>
  <xsl:apply-templates select="next"/>
  <xsl:text>"); // mapped by next dead
  eae.setServiceDist( ActiveEntry.</xsl:text>
  <xsl:apply-templates select="stat"/>
  <xsl:text>
  man.AddActiveState(eae);
  </xsl:text>

</xsl:template>

<xsl:template match="destroy">
  <xsl:text>
  eae = new ExternalActiveEntry(false); //destroy
  eae.SetId( "</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>"); //mapped by destroy id
  </xsl:text>
  <xsl:apply-templates select="prev"/>
  <xsl:text>man.AddActiveState(eae);
  </xsl:text>

</xsl:template>

<xsl:template match="act">
 <xsl:choose>
  <!-- Processing Interruptings Activities -->
  <xsl:when test="interrupting/@act|interrupted/@action">
  <xsl:text>
  intae = new InterruptActiveEntry(); //is interrupt
  intae.SetId( "</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>"); //mapped by act id
  intae.setServiceDist(ActiveEntry.</xsl:text>
  <xsl:call-template name="int_stat"/>
  <xsl:call-template name="int_tprev"/>
  <xsl:call-template name="int_entity"/>
  <xsl:if test="interrupting/@act">
  <xsl:text>
  intae.addInterrupt("</xsl:text>
  <xsl:value-of select="interrupting/@act"/>
  <xsl:text>");</xsl:text>
  </xsl:if>
  <xsl:text>
  man.AddActiveState(intae);

  </xsl:text>
 </xsl:when>

 <xsl:otherwise>

  <xsl:text>
  iae = new InternalActiveEntry(false); //isn't router
  iae.SetId( "</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>"); //mapped by act id
  iae.setServiceDist(ActiveEntry.</xsl:text>
  <xsl:apply-templates select="stat"/>
  <xsl:choose>
  <xsl:when test="whenprev/@cond">
   <xsl:apply-templates select="whenprev"/>
  </xsl:when>
  <xsl:otherwise>
  <xsl:text>
  iae.addCond("true");</xsl:text>
  //  <xsl:text>iae.addCond("</xsl:text><xsl:value-of select="@condition"/><xsl:text>"); </xsl:text>
  </xsl:otherwise>
  </xsl:choose>
  <xsl:apply-templates select="entity_class"/>
  <xsl:text>
  // Extended XACDML to support SPEM 2.0 - Rodrigo Pagliares
  iae.setSpemType("</xsl:text>
  <xsl:value-of select="@spem_type"/>
  <xsl:text>"); //mapped by spem_type
  iae.setParent("</xsl:text>
  <xsl:value-of select="@parent"/>
  <xsl:text>"); //mapped by parent
  iae.setDependencyType("</xsl:text>
  <xsl:value-of select="@dependency_type"/>
  <xsl:text>"); //mapped by dependency_type
  iae.setConditionToProcess("</xsl:text>
  <xsl:value-of select="@condition_to_process"/>
  <xsl:text>"); //mapped by condition_to_process
  iae.setProcessingQuantity("</xsl:text>
  <xsl:value-of select="@processing_quantity"/>
  <xsl:text>"); //mapped processing_quantity
  iae.setTimeBox("</xsl:text>
  <xsl:value-of select="@timebox"/>
  <xsl:text>"); //mapped by timebox
  man.AddActiveState(iae);
  </xsl:text>
 </xsl:otherwise>
 </xsl:choose>


</xsl:template>

<xsl:template match="router">
  <xsl:text>
  iae = new InternalActiveEntry(false); //is a router
  iae.SetId("</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>"); //mapped by router id
  iae.setServiceDist(ActiveEntry.CONST);
  iae.setDistP1(1);</xsl:text>
  <xsl:apply-templates select="entity_class"/>
  <xsl:apply-templates select="whennext"/>

</xsl:template>

<xsl:template match="whennext|whenprev">
  <xsl:text>
  iae.addCond("</xsl:text>
  <xsl:choose>
  <xsl:when test="contains(@cond,'.LE.')">
  <xsl:variable name="temp" select="@cond"/>
  <xsl:variable name="prefix" select="substring-before($temp,'.LE.')"/>
  <xsl:variable name="sufix" select="substring-after($temp,'.LE.')"/>
  <xsl:value-of select="$prefix"/>
  <xsl:text> &lt;= </xsl:text>
  <xsl:value-of select="$sufix"/>
  </xsl:when>
  <xsl:when test="contains(@cond,'.LT.')">
  <xsl:variable name="temp" select="@cond"/>
  <xsl:variable name="prefix" select="substring-before($temp,'.LT.')"/>
  <xsl:variable name="sufix" select="substring-after($temp,'.LT.')"/>
  <xsl:value-of select="$prefix"/>
  <xsl:text> &lt; </xsl:text>
  <xsl:value-of select="$sufix"/>
  </xsl:when>
  <xsl:when test="contains(@cond,'.GE.')">
  <xsl:variable name="temp" select="@cond"/>
  <xsl:variable name="prefix" select="substring-before($temp,'.GE.')"/>
  <xsl:variable name="sufix" select="substring-after($temp,'.GE.')"/>
  <xsl:value-of select="$prefix"/>
  <xsl:text> &gt;= </xsl:text>
  <xsl:value-of select="$sufix"/>
  </xsl:when>
  <xsl:when test="contains(@cond,'.GT.')">
  <xsl:variable name="temp" select="@cond"/>
  <xsl:variable name="prefix" select="substring-before($temp,'.GT.')"/>
  <xsl:variable name="sufix" select="substring-after($temp,'.GT.')"/>
  <xsl:value-of select="$prefix"/>
  <xsl:text> &gt; </xsl:text>
  <xsl:value-of select="$sufix"/>
  </xsl:when>
  <xsl:otherwise>
  <xsl:value-of select="@cond"/>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:text>");</xsl:text>
</xsl:template>


<xsl:template match="generate/next">
  <xsl:value-of select="@dead"/>
</xsl:template>


<xsl:template match="destroy/prev">
  <xsl:text>eae.setQID( "</xsl:text>
  <xsl:value-of select="@dead"/>
  <xsl:text>"); // mapped by prev dead
  </xsl:text>
</xsl:template>

<xsl:template match="entity_class">
  <xsl:variable name="queue2" select="@next"/>
  <xsl:for-each select="/acd/dead">
  <xsl:if test="(@id = $queue2) and (type/@init != type/@size)">
  <xsl:text>
  iae.addToQueue("</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");// mapped by next dead</xsl:text>
  </xsl:if>
  <xsl:if test="(@id = $queue2) and (type/@init = type/@size)">
  <xsl:text>
  iae.addToResource("</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");// mapped by next resource dead
  iae.addResourceQty(new Integer(</xsl:text>
  <xsl:value-of select="type/@init"/>
  <xsl:text>));// mapped by init on resource dead</xsl:text>
  </xsl:if>
  </xsl:for-each>
  <xsl:variable name="queue" select="@prev"/>
  <xsl:for-each select="/acd/dead">
  <xsl:if test="(@id = $queue) and (type/@init != type/@size)">
  <xsl:text>
  iae.addFromQueue("</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");// mapped by prev dead</xsl:text>
  </xsl:if>
  <xsl:if test="(@id = $queue) and (type/@init = type/@size)">
  <xsl:text>
  iae.addFromResource("</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");// mapped by prev resource dead</xsl:text>
  </xsl:if>
  </xsl:for-each>
</xsl:template>


<xsl:template match="act/stat|router/stat">
  <xsl:value-of select="@type"/>
  <xsl:text>);// mapped by stat type</xsl:text>
  <xsl:if test="@parm1">
  <xsl:text>
  iae.setDistP1((int)</xsl:text>
  <xsl:value-of select="@parm1"/>
  <xsl:text>);// mapped by parm1</xsl:text>
  </xsl:if>
  <xsl:if test="@parm2">
  <xsl:text>
  iae.setDistP2((int)</xsl:text>
  <xsl:value-of select="@parm2"/>
  <xsl:text>); //mapped by parm2</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template match="stat">
  <xsl:value-of select="@type"/>
  <xsl:text>);// mapped by stat type</xsl:text>
  <xsl:if test="@parm1">
  <xsl:text>
  eae.setDistP1((int)</xsl:text>
  <xsl:value-of select="@parm1"/>
  <xsl:text>);// mapped by parm1</xsl:text>
  </xsl:if>
  <xsl:if test="@parm2">
  <xsl:text>
  eae.setDistP2((int)</xsl:text>
  <xsl:value-of select="@parm2"/>
  <xsl:text>); //mapped by parm2</xsl:text>
  </xsl:if>
</xsl:template>

<xsl:template name="int_stat">
  <xsl:value-of select="stat/@type"/>
  <xsl:text>);// mapped by stat type</xsl:text>
  <xsl:if test="stat/@parm1">
  <xsl:text>
  intae.setDistP1((int)</xsl:text>
  <xsl:value-of select="stat/@parm1"/>
  <xsl:text>);// mapped by parm1</xsl:text>
  </xsl:if>
  <xsl:if test="stat/@parm2">
  <xsl:text>
  intae.setDistP2((int)</xsl:text>
  <xsl:value-of select="stat/@parm2"/>
  <xsl:text>); //mapped by parm2</xsl:text>
  </xsl:if>
</xsl:template>


<xsl:template name="int_tprev">
<xsl:choose>
<xsl:when test="whenprev/@cond">
<xsl:call-template name="int_prev"/>
</xsl:when>
<xsl:otherwise>
<xsl:text>
  intae.addCond("true");</xsl:text>
</xsl:otherwise>
</xsl:choose>
</xsl:template>



<xsl:template name="int_prev">
  <xsl:for-each select="whenprev">
  <xsl:text>
  intae.addCond("</xsl:text>
  <xsl:choose>
  <xsl:when test="contains(@cond,'.LE.')">
  <xsl:variable name="temp" select="@cond"/>
  <xsl:variable name="prefix" select="substring-before($temp,'.LE.')"/>
  <xsl:variable name="sufix" select="substring-after($temp,'.LE.')"/>
  <xsl:value-of select="$prefix"/>
  <xsl:text> &lt;= </xsl:text>
  <xsl:value-of select="$sufix"/>
  </xsl:when>
  <xsl:when test="contains(@cond,'.LT.')">
  <xsl:variable name="temp" select="@cond"/>
  <xsl:variable name="prefix" select="substring-before($temp,'.LT.')"/>
  <xsl:variable name="sufix" select="substring-after($temp,'.LT.')"/>
  <xsl:value-of select="$prefix"/>
  <xsl:text> &lt; </xsl:text>
  <xsl:value-of select="$sufix"/>
  </xsl:when>
  <xsl:when test="contains(@cond,'.GE.')">
  <xsl:variable name="temp" select="@cond"/>
  <xsl:variable name="prefix" select="substring-before($temp,'.GE.')"/>
  <xsl:variable name="sufix" select="substring-after($temp,'.GE.')"/>
  <xsl:value-of select="$prefix"/>
  <xsl:text> &gt;= </xsl:text>
  <xsl:value-of select="$sufix"/>
  </xsl:when>
  <xsl:when test="contains(@cond,'.GT.')">
  <xsl:variable name="temp" select="@cond"/>
  <xsl:variable name="prefix" select="substring-before($temp,'.GT.')"/>
  <xsl:variable name="sufix" select="substring-after($temp,'.GT.')"/>
  <xsl:value-of select="$prefix"/>
  <xsl:text> &gt; </xsl:text>
  <xsl:value-of select="$sufix"/>
  </xsl:when>
  <xsl:otherwise>
  <xsl:value-of select="@cond"/>
  </xsl:otherwise>
  </xsl:choose>
  <xsl:text>");</xsl:text>
  </xsl:for-each>
</xsl:template>

<xsl:template name="int_entity">
  <xsl:for-each select="entity_class">
  <xsl:variable name="queue" select="@next"/>
  <xsl:for-each select="/acd/dead">
  <xsl:if test="(@id = $queue) and (type/@init != type/@size)">
  <xsl:text>
  intae.addToQueue("</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");// mapped by next dead</xsl:text>
  </xsl:if>
  <xsl:if test="(@id = $queue) and (type/@init = type/@size)">
  <xsl:text>
  intae.addToResource("</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");// mapped by next resource dead
  intae.addResourceQty(new Integer(</xsl:text>
  <xsl:value-of select="type/@init"/>
  <xsl:text>));// mapped by init on resource dead</xsl:text>
  </xsl:if>
  </xsl:for-each>
  <xsl:variable name="queue2" select="@prev"/>
  <xsl:for-each select="/acd/dead">
  <xsl:if test="(@id = $queue2) and (type/@init != type/@size)">
  <xsl:text>
  intae.addFromQueue("</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");// mapped by prev dead</xsl:text>
  </xsl:if>
  <xsl:if test="(@id = $queue) and (type/@init = type/@size)">
  <xsl:text>
  intae.addFromResource("</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");// mapped by prev resource dead</xsl:text>
  </xsl:if>
  </xsl:for-each>
  </xsl:for-each>
</xsl:template>

<xsl:template name="dead_obs">
  <xsl:for-each select="dead">
  <xsl:if test="(type/@init = type/@size) and observer/@name ">
  <xsl:text>
  oe= new ObserverEntry(ObserverEntry.RESOURCE,"</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");//observer resource
  oe.SetId("</xsl:text>
  <xsl:value-of select="observer/@name"/>
  <xsl:text>");
  man.AddObserver(oe);
  </xsl:text>
  </xsl:if>
  <xsl:if test="(type/@init != type/@size) and observer/@name ">
  <xsl:text>
  oe= new ObserverEntry(ObserverEntry.QUEUE,"</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");//observer queue
  oe.SetId("</xsl:text>
  <xsl:value-of select="observer/@name"/>
  <xsl:text>");
  man.AddObserver(oe);
  </xsl:text>
  </xsl:if>
  </xsl:for-each>
</xsl:template>

<xsl:template name="gen_obs">
  <xsl:for-each select="generate|destroy">
  <xsl:if test="observer/@name">
  <xsl:text>
  oe= new ObserverEntry(ObserverEntry.</xsl:text>
  <xsl:value-of select="observer/@type"/>
  <xsl:text>,"</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");
  oe.SetId("</xsl:text>
  <xsl:value-of select="observer/@name"/>
  <xsl:text>");
  man.AddObserver(oe);

  eae = (ExternalActiveEntry)man.GetActiveState("</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");
  eae.setObsid("</xsl:text>
  <xsl:value-of select="observer/@name"/>
  <xsl:text>");
  </xsl:text>
  </xsl:if>
  </xsl:for-each>
</xsl:template>


<xsl:template name="act_obs">
  <xsl:for-each select="act|router">
  <xsl:if test="observer/@name">
  <xsl:text>
  oe= new ObserverEntry(ObserverEntry.</xsl:text>
  <xsl:value-of select="observer/@type"/>
  <xsl:text>,"</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");
  oe.SetId("</xsl:text>
  <xsl:value-of select="observer/@name"/>
  <xsl:text>");
  man.AddObserver(oe);
  
  </xsl:text>
  <!--Interrupts-->
  <xsl:choose>
  <xsl:when test="interrupt/@act">
  <xsl:text>

  intae = (InterruptActiveEntry)man.GetActiveState("</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");
  intae.setObsid("</xsl:text>
  <xsl:value-of select="observer/@name"/>
  <xsl:text>");
  </xsl:text>
 </xsl:when>

 <xsl:otherwise>

  <xsl:variable name="actint">
  <xsl:variable name="intact" select="@id"/>
  <xsl:for-each select="../act">
  <xsl:if test="interrupt/@act = $intact">
  <xsl:value-of select="interrupt/@act"/>
  </xsl:if>
  </xsl:for-each>
  </xsl:variable>
  <xsl:if test="$actint!=''">
  <xsl:text>

  intae = (InterruptActiveEntry)man.GetActiveState("</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");
  intae.setObsid("</xsl:text>
  <xsl:value-of select="observer/@name"/>
  <xsl:text>");
  </xsl:text>


  </xsl:if>
  <xsl:if test="$actint=''">
  <!--Activities and Routers Observers-->
  <xsl:text>

  iae = (InternalActiveEntry)man.GetActiveState("</xsl:text>
  <xsl:value-of select="@id"/>
  <xsl:text>");
  iae.setObsid("</xsl:text>
  <xsl:value-of select="observer/@name"/>
  <xsl:text>");
  </xsl:text>
  </xsl:if>
  </xsl:otherwise>
  </xsl:choose>
  </xsl:if>
  </xsl:for-each>
</xsl:template>


<!-- Text -->
<xsl:template match="text()">
<xsl:value-of select="normalize-space()"/>
</xsl:template>

</xsl:stylesheet>
