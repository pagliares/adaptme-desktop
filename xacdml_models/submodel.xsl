<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
version="1.0"
>
<xsl:output method="text"/>
<!-- Top Level -->
<xsl:template match="/">
<xsl:text>&lt;?xml version="1.0" encoding="ISO-8859-1"?&gt;
&lt;!DOCTYPE acd PUBLIC "acd description//EN" "xacdml.dtd"&gt;
&lt;acd id="MODEL_</xsl:text>
<xsl:apply-templates/>
<xsl:text>&lt;/acd&gt;
</xsl:text>
</xsl:template>

<!-- Processing ACD -->
<xsl:template match="acd">
<xsl:value-of select="@id"/>
<xsl:text>"&gt;
</xsl:text>


<!-- Processing elements of ACD with submodels -->
<xsl:apply-templates select="simtime"/>
<xsl:apply-templates select="connect"/>
<xsl:apply-templates select="instance"/>
<xsl:apply-templates select="generate"/>
<xsl:apply-templates select="dead"/>
<xsl:apply-templates select="act"/>
<xsl:apply-templates select="router"/>
<xsl:apply-templates select="destroy"/>

</xsl:template>

<!-- Simulation Time of ACD -->

<xsl:template match="simtime">
<xsl:text>&lt;simtime time="</xsl:text>
<xsl:value-of select="@time"/>
<xsl:text>"/&gt;

</xsl:text>
</xsl:template>

<!-- Instances of submodels -->

<xsl:template match="instance">
<xsl:variable name="temp" select="@model"/>
<xsl:variable name="smid" select="substring-before($temp,']')"/>
<xsl:text>
</xsl:text>
<xsl:variable name="aux" select="concat($smid,'_')"/>

<xsl:call-template name="subdead">
<xsl:with-param name="name">
<xsl:value-of select="$aux"/>
</xsl:with-param>
</xsl:call-template>

<xsl:call-template name="subgenerate">
<xsl:with-param name="name">
<xsl:value-of select="$aux"/>
</xsl:with-param>
</xsl:call-template>

<xsl:call-template name="subact">
<xsl:with-param name="name">
<xsl:value-of select="$aux"/>
</xsl:with-param>
</xsl:call-template>

<xsl:call-template name="subrouter">
<xsl:with-param name="name">
<xsl:value-of select="$aux"/>
</xsl:with-param>
</xsl:call-template>


<xsl:call-template name="subdestroy">
<xsl:with-param name="name">
<xsl:value-of select="$aux"/>
</xsl:with-param>
</xsl:call-template>

</xsl:template>

<!-- Processing Connect -->
<xsl:template match="connect">

<xsl:if test="@submodel.in">
 <xsl:variable name="temp" select="@submodel.in"/>
 <xsl:variable name="sm" select="substring-before($temp,'[')"/>
 <xsl:variable name="temp" select="substring-after($temp,'[')"/>
 <xsl:variable name="inst" select="substring-before($temp,']')"/>
 <xsl:variable name="idel" select="substring-after($temp,'].')"/>
 <xsl:text>&lt;dead id="</xsl:text>
 <xsl:value-of select="concat($sm,'_',$inst,'_',$idel)"/>
 <xsl:text>" class="</xsl:text>
 <xsl:variable name="class">
 <xsl:for-each select="../submodel">
 <xsl:if test="@id = $sm">
 <xsl:for-each select="interface">
 <xsl:if test="in/@id = $idel">
 <xsl:value-of select="in/@class"/>
 </xsl:if>
 </xsl:for-each>
 </xsl:if>
 </xsl:for-each>
 </xsl:variable>
 <xsl:value-of select="$class"/>
 <xsl:text>"&gt;
 &lt;type struct="QUEUE" size="10" init="0"/&gt;
&lt;/dead&gt;

</xsl:text>
</xsl:if>

<!-- Processing Submodel.Out -->

<xsl:if test="@submodel.out">
 <xsl:variable name="temp" select="@submodel.out"/>
 <xsl:variable name="smout" select="substring-before($temp,'[')"/>
 <xsl:variable name="temp" select="substring-after($temp,'[')"/>
 <xsl:variable name="instout" select="substring-before($temp,']')"/>
 <xsl:variable name="idelout" select="substring-after($temp,'].')"/>

 <xsl:text>&lt;act id="</xsl:text>
 <xsl:value-of select="concat($smout,'_',$instout,'_',$idelout)"/>
 <xsl:text>"&gt;</xsl:text>
 <xsl:for-each select="../submodel">
 <xsl:if test="@id = $smout">
 <xsl:for-each select="interface/out">
 <xsl:if test="@id = $idelout">
  <xsl:text>
 &lt;stat type="</xsl:text>
  <xsl:choose>
   <xsl:when test="stat/@type">
    <xsl:value-of select="stat/@type"/>
    <xsl:if test="stat/@parm1">
     <xsl:text>" parm1="</xsl:text>
     <xsl:value-of select="stat/@parm1"/>
    </xsl:if>
    <xsl:if test="stat/@parm2">
     <xsl:text>" parm2="</xsl:text>
     <xsl:value-of select="stat/@parm2"/>
    </xsl:if>
    <xsl:text>" /&gt;</xsl:text>
   </xsl:when>
   <xsl:otherwise>
    <xsl:text>CONST" parm1="0"/&gt;</xsl:text>
   </xsl:otherwise>
  </xsl:choose>
 </xsl:if>
 </xsl:for-each>
 </xsl:if>
 </xsl:for-each>
 <xsl:variable name="prev_dead">
 <xsl:for-each select="../submodel">
 <xsl:if test="@id = $smout">
 <xsl:for-each select="interface">
 <xsl:if test="out/@id = $idelout">
  <xsl:value-of select="concat($smout,'_',$instout,'_',out/prev/@dead)"/>
 </xsl:if>
 </xsl:for-each>
 </xsl:if>
 </xsl:for-each>
 </xsl:variable>
 <xsl:text>
 &lt;entity_class prev="</xsl:text>
 <xsl:value-of select="$prev_dead"/>
 <xsl:text>"  next="</xsl:text>
 <!-- Connecting Submodel.Out with Submodel.In -->
 <xsl:choose>
 <xsl:when test="@submodel.in">
 <xsl:variable name="temp" select="@submodel.in"/>
 <xsl:variable name="sm" select="substring-before($temp,'[')"/>
 <xsl:variable name="temp" select="substring-after($temp,'[')"/>
 <xsl:variable name="inst" select="substring-before($temp,']')"/>
 <xsl:variable name="idel" select="substring-after($temp,'].')"/>
 <xsl:value-of select="concat($sm,'_',$inst,'_',$idel)"/>
 </xsl:when>
 <!-- Comnecting Submodel.Out with Dead -->
 <xsl:otherwise>
 <xsl:value-of select="@dead"/>
 </xsl:otherwise>
</xsl:choose>
 <xsl:text>"/&gt;
&lt;/act&gt;

</xsl:text>
</xsl:if>
</xsl:template>



<!-- Generates of ACD -->

<xsl:template match="generate">
<xsl:text>&lt;generate id="</xsl:text>
<xsl:value-of select="@id"/>
 <xsl:text>" class="</xsl:text>
 <xsl:value-of select="@class"/>
<xsl:text>"&gt;
 &lt;stat type="</xsl:text>
<xsl:value-of select="stat/@type"/>
<xsl:if test="stat/@parm1">
 <xsl:text>" parm1="</xsl:text>
 <xsl:value-of select="stat/@parm1"/>
 <xsl:if test="stat/@parm2">
  <xsl:text>" parm2="</xsl:text>
  <xsl:value-of select="stat/@parm2"/>
 </xsl:if>
</xsl:if>
<xsl:text>"/&gt;
 &lt;next dead="</xsl:text>
<xsl:choose>
 <xsl:when test="contains(next/@dead,'[')">
  <xsl:variable name="temp" select="next/@dead"/>
  <xsl:variable name="sm" select="substring-before($temp,'[')"/>
  <xsl:variable name="temp" select="substring-after($temp,'[')"/>
  <xsl:variable name="inst" select="substring-before($temp,']')"/>
  <xsl:variable name="idel" select="substring-after($temp,'].')"/>
  <xsl:value-of select="concat($sm,'_',$inst,'_',$idel)"/>
 </xsl:when>
 <xsl:otherwise>
  <xsl:value-of select="next/@dead"/>
 </xsl:otherwise>
</xsl:choose>
<xsl:text>"/&gt;</xsl:text>
 <xsl:apply-templates select="graphic"/>
 <xsl:apply-templates select="observer"/>
<xsl:text>
&lt;/generate&gt;

</xsl:text>
</xsl:template>


<!-- Processing Destroys of ACD -->

<xsl:template match="destroy">
<xsl:text>&lt;destroy id="</xsl:text>
<xsl:value-of select="@id"/>
 <xsl:text>" class="</xsl:text>
 <xsl:value-of select="@class"/>
 <xsl:text>"&gt;
 &lt;prev dead="</xsl:text>
  <xsl:value-of select="prev/@dead"/>
 <xsl:text>"/&gt;</xsl:text>
 <xsl:apply-templates select="graphic"/>
 <xsl:apply-templates select="observer"/>
<xsl:text>
&lt;/destroy&gt;

</xsl:text>
</xsl:template>


<!-- Processing Deads of ACD -->

<xsl:template match="dead">

<xsl:text>&lt;dead id="</xsl:text>
<xsl:value-of select="@id"/>
<xsl:text>" class="</xsl:text>
<xsl:value-of select="@class"/>
<xsl:text>"&gt;
 &lt;type struct="</xsl:text>
 <xsl:value-of select="type/@struct"/>
 <xsl:text>" size="</xsl:text>
 <xsl:value-of select="type/@size"/>
 <xsl:text>" init="</xsl:text>
 <xsl:value-of select="type/@init"/>
 <xsl:text>"/&gt;</xsl:text>
 <xsl:apply-templates select="graphic"/>
 <xsl:apply-templates select="observer"/>
<xsl:text>
&lt;/dead&gt;

</xsl:text>
</xsl:template>



<!-- Processing Activities of ACD -->

<xsl:template match="act">

<xsl:text>&lt;act id="</xsl:text>
<xsl:value-of select="@id"/>
<xsl:text>"&gt;
 &lt;stat type="</xsl:text>
<xsl:value-of select="stat/@type"/>
<xsl:if test="stat/@parm1">
 <xsl:text>" parm1="</xsl:text>
 <xsl:value-of select="stat/@parm1"/>
 <xsl:if test="stat/@parm2">
  <xsl:text>" parm2="</xsl:text>
  <xsl:value-of select="stat/@parm2"/>
 </xsl:if>
</xsl:if>
<xsl:text>"/&gt;</xsl:text>

<xsl:apply-templates select="entity_class"/>

<xsl:for-each select="whenprev">
 <xsl:text>
 &lt;whenprev cond="</xsl:text>
 <xsl:value-of select="@cond"/>
 <xsl:text>"&gt;
   &lt;entity prev="</xsl:text>
   <xsl:choose>
   <xsl:when test="contains(entity_class/@prev,'[')">
    <xsl:variable name="temp" select="entity_class/@prev"/>
    <xsl:variable name="sm" select="substring-before($temp,'[')"/>
    <xsl:variable name="temp" select="substring-after($temp,'[')"/>
    <xsl:variable name="inst" select="substring-before($temp,']')"/>
    <xsl:variable name="idel" select="substring-after($temp,'].')"/>
    <xsl:value-of select="concat($sm,'_',$inst,'_',$idel)"/>
   </xsl:when>
   <xsl:otherwise>
    <xsl:value-of select="entity_class/@prev"/>
   </xsl:otherwise>
   </xsl:choose>
   <xsl:text>" next="</xsl:text>
   <xsl:choose>
   <xsl:when test="contains(entity_class/@next,'[')">
    <xsl:variable name="temp" select="@next"/>
    <xsl:variable name="sm" select="substring-before($temp,'[')"/>
    <xsl:variable name="temp" select="substring-after($temp,'[')"/>
    <xsl:variable name="inst" select="substring-before($temp,']')"/>
    <xsl:variable name="idel" select="substring-after($temp,'].')"/>
    <xsl:value-of select="concat($sm,'_',$inst,'_',$idel)"/>
   </xsl:when>
   <xsl:otherwise>
    <xsl:value-of select="entity_class/@next"/>
   </xsl:otherwise>
   </xsl:choose>
   <xsl:text>"/&gt;
  &lt;/whenprev&gt;</xsl:text>
 </xsl:for-each>
 <xsl:apply-templates select="interrupting"/>
 <xsl:apply-templates select="interrupted"/>
 <xsl:apply-templates select="graphic"/>
 <xsl:apply-templates select="observer"/>
<xsl:text>
&lt;/act&gt;

</xsl:text>
</xsl:template>



<!-- Processing Routers of ACD -->

<xsl:template match="router">

<xsl:text>&lt;router id="</xsl:text>
<xsl:value-of select="@id"/>
<xsl:text>"&gt;
 &lt;stat type="</xsl:text>
<xsl:value-of select="stat/@type"/>
<xsl:if test="stat/@parm1">
 <xsl:text>" parm1="</xsl:text>
 <xsl:value-of select="stat/@parm1"/>
 <xsl:if test="stat/@parm2">
  <xsl:text>" parm2="</xsl:text>
  <xsl:value-of select="stat/@parm2"/>
 </xsl:if>
</xsl:if>
<xsl:text>"/&gt;</xsl:text>

<xsl:apply-templates select="entity_class"/>

<xsl:for-each select="whennext">
 <xsl:text>
 &lt;whennext cond="</xsl:text>
 <xsl:value-of select="@cond"/>
 <xsl:text>"&gt;
   &lt;entity prev="</xsl:text>
   <xsl:choose>
   <xsl:when test="contains(entity_class/@prev,'[')">
    <xsl:variable name="temp" select="entity_class/@prev"/>
    <xsl:variable name="sm" select="substring-before($temp,'[')"/>
    <xsl:variable name="temp" select="substring-after($temp,'[')"/>
    <xsl:variable name="inst" select="substring-before($temp,']')"/>
    <xsl:variable name="idel" select="substring-after($temp,'].')"/>
    <xsl:value-of select="concat($sm,'_',$inst,'_',$idel)"/>
   </xsl:when>
   <xsl:otherwise>
    <xsl:value-of select="entity_class/@prev"/>
   </xsl:otherwise>
   </xsl:choose>
   <xsl:text>" next="</xsl:text>
   <xsl:choose>
   <xsl:when test="contains(entity_class/@next,'[')">
    <xsl:variable name="temp" select="entity_class/@next"/>
    <xsl:variable name="sm" select="substring-before($temp,'[')"/>
    <xsl:variable name="temp" select="substring-after($temp,'[')"/>
    <xsl:variable name="inst" select="substring-before($temp,']')"/>
    <xsl:variable name="idel" select="substring-after($temp,'].')"/>
    <xsl:value-of select="concat($sm,'_',$inst,'_',$idel)"/>
   </xsl:when>
   <xsl:otherwise>
    <xsl:value-of select="entity_class/@next"/>
   </xsl:otherwise>
   </xsl:choose>
   <xsl:text>"/&gt;
  &lt;/whennext&gt;</xsl:text>
 </xsl:for-each>
 <xsl:apply-templates select="graphic"/>
 <xsl:apply-templates select="observer"/>
<xsl:text>
&lt;/router&gt;

</xsl:text>
</xsl:template>


<!-- Processing Internal Elements of Submodels -->

<!-- Generates of Submodels -->
<xsl:template name="subgenerate">
<xsl:param name="name"/>
<xsl:for-each select="/acd/submodel/generate">
<xsl:variable name="smid" select="substring-before($name,'[')"/>
<xsl:variable name="aux"  select="concat($smid,'_',substring-after($name,'['))"/>
<xsl:variable name="temp" select="concat($aux,@id)"/>
<xsl:text>&lt;generate id="</xsl:text>
<xsl:value-of select="concat($aux,@id)"/>
<xsl:text>" class="</xsl:text>
<xsl:value-of select="@class"/>
<xsl:text>"&gt;
 &lt;stat type="</xsl:text>
<xsl:value-of select="stat/@type"/>
<xsl:if test="stat/@parm1">
 <xsl:text>" parm1="</xsl:text>
 <xsl:value-of select="stat/@parm1"/>
 <xsl:if test="stat/@parm2">
  <xsl:text>" parm2="</xsl:text>
  <xsl:value-of select="stat/@parm2"/>
 </xsl:if>
 </xsl:if>
<xsl:text>"/&gt;
 &lt;next dead="</xsl:text>
  <xsl:value-of select="concat($aux,next/@dead)"/>
 <xsl:text>"/&gt;</xsl:text>
 <xsl:apply-templates select="graphic"/>
 <xsl:call-template name="subobserver">
 <xsl:with-param name="name">
 <xsl:value-of select="concat($name,']',@id)"/>
 </xsl:with-param>
 </xsl:call-template>
<xsl:text>
&lt;/generate&gt;

</xsl:text>
</xsl:for-each>
</xsl:template>

<!-- Destroys of Submodels -->
<xsl:template name="subdestroy">
<xsl:param name="name"/>
<xsl:for-each select="/acd/submodel/destroy">
<xsl:variable name="smid" select="substring-before($name,'[')"/>
<xsl:variable name="aux" select="concat($smid,'_',substring-after($name,'['),@id)"/>
<xsl:if test="../@id=$smid">
<xsl:text>&lt;destroy id="</xsl:text>
<xsl:value-of select="concat($aux,@id)"/>
<xsl:text>" class="</xsl:text>
<xsl:value-of select="@class"/>
 <xsl:text>"&gt;
 &lt;prev dead="</xsl:text>
  <xsl:if test="prev/@dead">
  <xsl:value-of select="concat($aux,prev/@dead)"/>
  </xsl:if>
  <xsl:if test="prev/@in">
  <xsl:value-of select="concat($aux,prev/@in)"/>
  </xsl:if>
 <xsl:text>"/&gt;</xsl:text>
 <xsl:apply-templates select="graphic"/>
 <xsl:call-template name="subobserver">
 <xsl:with-param name="name">
 <xsl:value-of select="concat($name,']',@id)"/>
 </xsl:with-param>
 </xsl:call-template>
<xsl:text>
&lt;/destroy&gt;

</xsl:text>
</xsl:if>
</xsl:for-each>
</xsl:template>

<!-- Deads of Submodels -->
<xsl:template name="subdead">
<xsl:param name="name"/>
<xsl:for-each select="/acd/submodel/dead">
<xsl:variable name="smid" select="substring-before($name,'[')"/>
<xsl:variable name="temp" select="concat($smid,'_',substring-after($name,'['),@id)"/>
<xsl:if test="../@id=$smid">
<xsl:text>&lt;dead id="</xsl:text>
<xsl:value-of select="$temp"/>
<xsl:text>"&gt;
 &lt;type struct="</xsl:text>
<xsl:value-of select="type/@struct"/>
<xsl:text>" size="</xsl:text>
<xsl:value-of select="type/@size"/>
<xsl:text>" init="</xsl:text>
<xsl:value-of select="type/@init"/>
<xsl:text>"/&gt;</xsl:text>
 <xsl:apply-templates select="graphic"/>
 <xsl:call-template name="subobserver">
 <xsl:with-param name="name">
 <xsl:value-of select="concat($name,']',@id)"/>
 </xsl:with-param>
 </xsl:call-template>
<xsl:text>
&lt;/dead&gt;

</xsl:text>
</xsl:if>
</xsl:for-each>
</xsl:template>

<!-- Activities of Submodels -->
<xsl:template name="subact">
<xsl:param name="name"/>
<xsl:for-each select="/acd/submodel/act">
<xsl:variable name="smid" select="substring-before($name,'[')"/>
<xsl:variable name="aux"  select="concat($smid,'_',substring-after($name,'['))"/>
<xsl:variable name="temp" select="concat($aux,@id)"/>
<xsl:if test="../@id=$smid">
<xsl:text>&lt;act id="</xsl:text>
<xsl:value-of select="$temp"/>
<xsl:text>"&gt;
 &lt;stat type="</xsl:text>
<xsl:value-of select="stat/@type"/>
<xsl:if test="stat/@parm1">
<xsl:text>" parm1="</xsl:text>
<xsl:value-of select="stat/@parm1"/>
<xsl:if test="stat/@parm2">
<xsl:text>" parm2="</xsl:text>
<xsl:value-of select="stat/@parm2"/>
</xsl:if>
</xsl:if>
<xsl:text>"/&gt;</xsl:text>
<xsl:for-each select="entity_class">
 <xsl:text>
 &lt;entity prev="</xsl:text>
  <xsl:value-of select="concat($aux,@prev)"/>
 <xsl:text>" next="</xsl:text>
 <xsl:value-of select="concat($aux,@next)"/>
 <xsl:text>"/&gt;</xsl:text>
</xsl:for-each>
<xsl:for-each select="whenprev">
 <xsl:text>
 &lt;whenprev cond="</xsl:text>
 <xsl:value-of select="concat($aux,entity_class/@cond)"/>
 <xsl:text>"&gt;
   &lt;entity prev="</xsl:text>
    <xsl:value-of select="@prev"/>
   <xsl:text>" next="</xsl:text>
    <xsl:value-of select="concat($aux,entity_class/@next)"/>
   <xsl:text>"/&gt;
  &lt;/whenprev&gt;</xsl:text>
  <xsl:apply-templates select="interrupting"/>
  <xsl:apply-templates select="interrupted"/>
 </xsl:for-each>
 <xsl:apply-templates select="graphic"/>
 <xsl:call-template name="subobserver">
 <xsl:with-param name="name">
 <xsl:value-of select="concat($name,']',@id)"/>
 </xsl:with-param>
 </xsl:call-template>
<xsl:text>
&lt;/act&gt;

</xsl:text>
</xsl:if>
</xsl:for-each>
</xsl:template>


<!-- Routers of Submodels -->
<xsl:template name="subrouter">
<xsl:param name="name"/>
<xsl:for-each select="/acd/submodel/router">
<xsl:variable name="smid" select="substring-before($name,'[')"/>
<xsl:variable name="aux"  select="concat($smid,'_',substring-after($name,'['))"/>
<xsl:variable name="temp" select="concat($aux,@id)"/>
<xsl:if test="../@id=$smid">
<xsl:text>&lt;router id="</xsl:text>
<xsl:value-of select="$temp"/>
<xsl:text>"&gt;
 &lt;stat type="</xsl:text>
<xsl:value-of select="stat/@type"/>
<xsl:if test="stat/@parm1">
 <xsl:text>" parm1="</xsl:text>
 <xsl:value-of select="stat/@parm1"/>
 <xsl:if test="stat/@parm2">
  <xsl:text>" parm2="</xsl:text>
  <xsl:value-of select="stat/@parm2"/>
 </xsl:if>
</xsl:if>
<xsl:text>"/&gt;</xsl:text>
<xsl:for-each select="entity_class">
 <xsl:text>
 &lt;entity prev="</xsl:text>
  <xsl:value-of select="concat($aux,@prev)"/>
 <xsl:text>" next="</xsl:text>
 <xsl:value-of select="concat($aux,@next)"/>
 <xsl:text>"/&gt;</xsl:text>
</xsl:for-each>

<xsl:for-each select="whennext">
 <xsl:text>
 &lt;whennext cond="</xsl:text>
 <xsl:value-of select="@cond"/>
 <xsl:text>"&gt;
   &lt;entity prev="</xsl:text>
    <xsl:value-of select="concat($aux,entity_class/@prev)"/>
   <xsl:text>" next="</xsl:text>
    <xsl:value-of select="concat($aux,entity_class/@next)"/>
   <xsl:text>"/&gt;
  &lt;/whennext&gt;</xsl:text>
</xsl:for-each>
<xsl:apply-templates select="graphic"/>
 <xsl:call-template name="subobserver">
 <xsl:with-param name="name">
 <xsl:value-of select="concat($name,']',@id)"/>
 </xsl:with-param>
 </xsl:call-template>
<xsl:text>
&lt;/router&gt;

</xsl:text>
</xsl:if>
</xsl:for-each>
</xsl:template>

<!-- Processing Element Observer for Submodels-->

<xsl:template name="subobserver">
<xsl:param name="name"/>
<xsl:for-each select="/acd/submodel/router/observer|/acd/submodel/act/observer|/acd/submodel/act/destroy/observer|/acd/submodel/generate/observer|/acd/submodel/destroy/observer|/acd/submodel/dead/observer">
<xsl:variable name="aux1" select="substring-after($name,']')"/>
<xsl:variable name="smid" select="substring-before($name,'[')"/>
<xsl:variable name="aux"  select="concat($smid,'_',substring-after($name,'['))"/>
<xsl:variable name="temp" select="concat($aux,@id)"/>
<xsl:variable name="aux1" select="substring-after($name,']')"/>
<xsl:if test="concat(../../@id,'_',../@id)=concat($smid,'_',$aux1)">
<xsl:text>
 &lt;observer type="</xsl:text>
<xsl:value-of select="@type"/>
<xsl:text>" name="</xsl:text>
<xsl:value-of select="concat(substring-before($aux,']'),@name)"/>
<xsl:text>"/&gt;</xsl:text>
</xsl:if>
</xsl:for-each>
</xsl:template>

<!-- Processing Element Interrupting -->

<xsl:template match="interrupting">
<xsl:text>
 &lt;interrupting act="</xsl:text>
<xsl:value-of select="@act"/>
<xsl:text>"/&gt;
</xsl:text>
</xsl:template>

<!-- Processing Element Interrupted -->

<xsl:template match="interrupted">
<xsl:text>
 &lt;interrupting action="</xsl:text>
<xsl:value-of select="@action"/>
<xsl:text>"/&gt;
</xsl:text>
</xsl:template>

<!-- Processing Element Graphic -->

<xsl:template match="graphic">
<xsl:text>
 &lt;graphic type="</xsl:text>
<xsl:value-of select="@type"/>
<xsl:text>" x="</xsl:text>
<xsl:value-of select="@x"/>
<xsl:text>" y="</xsl:text>
<xsl:value-of select="@y"/>
<xsl:text>"&gt;
</xsl:text>
</xsl:template>

<!-- Processing Element Observer -->

<xsl:template match="observer">
<xsl:text>
 &lt;observer type="</xsl:text>
<xsl:value-of select="@type"/>
<xsl:text>" name="</xsl:text>
<xsl:value-of select="@name"/>
<xsl:text>"/&gt;</xsl:text>
</xsl:template>

<!-- Processing Element Entity_Class -->
<xsl:template match="entity_class">
 <xsl:text>
 &lt;entity_class prev="</xsl:text>
 <xsl:value-of select="@prev"/>
 <xsl:text>" next="</xsl:text>
 <xsl:choose>
 <xsl:when test="contains(@next,'[')">
  <xsl:variable name="temp" select="@next"/>
  <xsl:variable name="sm" select="substring-before($temp,'[')"/>
  <xsl:variable name="temp" select="substring-after($temp,'[')"/>
  <xsl:variable name="inst" select="substring-before($temp,']')"/>
  <xsl:variable name="idel" select="substring-after($temp,'].')"/>
  <xsl:value-of select="concat($sm,'_',$inst,'_',$idel)"/>
 </xsl:when>
 <xsl:otherwise>
  <xsl:value-of select="@next"/>
 </xsl:otherwise>
 </xsl:choose>
 <xsl:text>"/&gt;</xsl:text>
</xsl:template>


</xsl:stylesheet>
