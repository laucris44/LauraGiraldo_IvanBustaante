window.addEventListener('load', function () {
    (function(){

      const url = '/turnos';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {
      //recorremos la colección de turnos del JSON
         for(turno of data){

            var table = document.getElementById("turnoTable");
            var turnoRow =table.insertRow();
            let tr_id = turno.id;
            turno.id = tr_id;

            let deleteButton = '<button' +
                                      ' id=' + '\"' + 'btn_delete_' + turno.id + '\"' +
                                      ' type="button" onclick="deleteBy('+turno.id+')" class="btn btn-danger btn_delete">' +
                                      '&times' +
                                      '</button>';


            let updateButton = '<button' +
                                      ' id=' + '\"' + 'btn_id_' + turno.id + '\"' +
                                      ' type="button" onclick="findBy('+turno.id+')" class="btn btn-info btn_id">' +
                                      turno.id +
                                      '</button>';

             let domicilio = turno.paciente.domicilio.calle + ', ' + turno.paciente.domicilio.numero + ', ' +
                                    turno.paciente.domicilio.localidad + ', ' + turno.paciente.domicilio.provincia;

            turnoRow.innerHTML = '<td>' + updateButton + '</td>' +
                     '<td>' + turno.odontologo.nombre + " " + turno.odontologo.apellido +'</td>' +
                     '<td>' + turno.paciente.nombre + " " + turno.paciente.apellido + '<br>' +
                     'Fecha de Ingreso: ' + turno.paciente.fechaIngreso + '<br>' +
                     'Domicilio: ' + domicilio + '<br>' +
                     'Email: ' + turno.paciente.email + '</td>' +
                     '<td>' + turno.fecha+ '</td>' +
                     '<td>' + deleteButton + '</td>';

        };

    })
    })

    (function(){
      let pathname = window.location.pathname;
      if (pathname == "/get_turnos.html") {
          document.querySelector(".nav .nav-item a:last").addClass("active");
      }
    })


    })