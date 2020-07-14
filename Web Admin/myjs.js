var user;
var selectedFile;

function handleFileSelect(event) {
	$(".upload-group").show();
	selectedFile = event.target.files[0];
};


function upload(){
	var metadata = {
		contentType: 'image',
		customMetadata: {
			'dogType': 'Lab',
			'title': $("#imgTitle").val(),
			'caption': $("#imgDesc").val()
		},
	};
	var uploadTask = firebase.storage().ref().child('dogImages/' + selectedFile.name).put(selectedFile, metadata);
	uploadTask.on('state_changed', function(snapshot){
  			}, function(error) {
  		
	}, function() {
  			$(".upload-group")[0].before("Success!");
  		$(".upload-group").hide();

	});
