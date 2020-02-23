// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable1').DataTable({
    autoWidth: false,
    ordering: false,
    searching: false
  });

  $('#dataTable2').DataTable({
    autoWidth: false,
    ordering: false,
    searching: false
  });
});
