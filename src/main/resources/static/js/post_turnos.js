window.addEventListener('load', function () {
    const formulario = document.querySelector('#add_new_turno');

    fetch('/odontologos')
            .then(response => response.json())
            .then(data => {
                const odontologoSelect = document.querySelector('#odontologo');
                data.forEach(odontologo => {
                    let option = document.createElement('option');
                    option.value = odontologo.id;
                    option.text = `${odontologo.nombre} ${odontologo.apellido}`;
                    odontologoSelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error('Error fetching odontologos:', error);
            });

        fetch('/paciente')
            .then(response => response.json())
            .then(data => {
                const pacienteSelect = document.querySelector('#paciente');
                data.forEach(paciente => {
                    let option = document.createElement('option');
                    option.value = paciente.id;
                    option.text = `${paciente.nombre} ${paciente.apellido}`;
                    pacienteSelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error('Error fetching pacientes:', error);
            });

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();
        const formData = {
            paciente: {
                            id: document.querySelector('#paciente').value
                        },
                        odontologo: {
                            id: document.querySelector('#odontologo').value
                        },
                        fechaHora: document.querySelector('#fecha').value
        };

        const url = '/turnos';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            //.then(response => response.json())
            .then(data => {
                    let successAlert = '<div class="alert alert-success alert-dismissible">' +
                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                     '<strong></strong> Turno agregado </div>'

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
                 resetUploadForm();
            })
            .catch(error => {
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                                     '<strong> Error intente nuevamente</strong> </div>'

                      document.querySelector('#response').innerHTML = errorAlert;
                      document.querySelector('#response').style.display = "block";
                     resetUploadForm();})
    });


     function resetUploadForm() {
            document.querySelector('#paciente').value = "";
            document.querySelector('#odontologo').value = "";
            document.querySelector('#fecha').value = "";
        }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/"){
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/get_turnos.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
});