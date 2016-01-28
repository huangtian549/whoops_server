<html>
<body>
<h2>POST!</h2>

<body><form action="/whoops/post/addForWeb" method="post" enctype="multipart/form-data">

<textarea rows="10" cols="200" name="content">

</textarea><br/>

<input type="file" name="file" accept="image/*" />
<input name="aa" type="button" value="Add picture button" onclick="addPic()"/>
<div id="selectPic"></div>
<br/>
<input name="activityId" type="hidden" value="1" />
<br/>
<input name="latitude" type="hidden" value="0" />
<br/>
<input name="longitude" type="hidden" value="0" />
<br/>
<input name="uid" type="hidden" value="999999999" />
<br/>
<input name="" type="submit" value="Submit"  /></form>

<script language="javascript">
function addPic(){
	var div = document.getElementById("selectPic");
	var textNode = '<input type="file" name="file" accept="image/*" /><br/>';
	div.innerHTML += textNode;
}
</script>
</body>
</html>
