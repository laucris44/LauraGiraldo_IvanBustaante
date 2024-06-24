window.addEventListener('load', function () {

    const formulario = document.querySelector('#update_turno_form');

    formulario.addEventListener('submit', function (event) {
        const formData = {
            id: document.querySelector('#turno_id').value,
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
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
          fetch(url,settings)
          .then(response => response.json())

    })
 })

    function findBy(id) {
          const url = '/turnos/' +id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
              let paciente = data;
              document.querySelector('#turno_id').value = turno.id || '';
                          document.querySelector('#odontologo').value = turno.odontologo.id || '',
                          document.querySelector('#paciente').value = turno.paciente.id || '',
                          document.querySelector('#fecha').value = turno.fecha || '';
                          // Mostrar el formulario de actualización
                          document.querySelector('#div_turno_updating').style.display = "block";
          }).catch(error => {
              alert("Error: " + error);
          })

          // Obtener lista de todos los odontólogos
              const urlOdontologos = '/odontologos';

              fetch(urlOdontologos)
                  .then(response => response.json())
                  .then(odontologos => {
                      let odontologoSelect = document.querySelector('#odontologo');
                      odontologoSelect.innerHTML = '';
                      odontologos.forEach(odontologo => {
                          let option = document.createElement('option');
                          option.value = odontologo.id;
                          option.textContent = `${odontologo.nombre} ${odontologo.apellido} (Matrícula: ${odontologo.matricula})`;
                          odontologoSelect.appendChild(option);
                      });
                  })
                  .catch(error => {
                      console.error("Error obteniendo lista de odontólogos:", error);
                  });

              // Obtener lista de todos los pacientes
              const urlPacientes = '/paciente';

              fetch(urlPacientes)
                  .then(response => response.json())
                  .then(pacientes => {
                      let pacienteSelect = document.querySelector('#paciente');
                      pacienteSelect.innerHTML = '';
                      pacientes.forEach(paciente => {
                          let option = document.createElement('option');
                          option.value = paciente.id;
                          option.textContent = `${paciente.nombre} ${paciente.apellido} (Cédula: ${paciente.cedula})`;
                          pacienteSelect.appendChild(option);
                      });
                  })
                  .catch(error => {
                      console.error("Error obteniendo lista de pacientes:", error);
                  });
      }