
console.log("contacts.js")
const baseURL="http://localhost:8081"
const viewContactModal=document.getElementById('view_contact_modal')

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    // backdropClasses:
    //     'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'view_contact_modal',
  override: true
};

const contactModal=new Modal(viewContactModal, options, instanceOptions);

//open modal
function openContactModal(){//modal will be shown when this funct is called
    contactModal.show(); //modal will be shown
}

//close modal
function closeContactModal(){
    contactModal.hide();
}

// load user contact data
async function loadContactData(id){
    console.log(id);
    //funct to call load data
    try{
        const data = await (await fetch(`${baseURL}/api/contacts/${id}`)
        ).json();
        console.log(data);
        
        document.querySelector("#contact_name").innerHTML=data.name;
        document.querySelector("#contact_email").innerHTML=data.email;

    document.querySelector("#contact_image").src = data.picture;
    document.querySelector("#contact_address").innerHTML = data.address;
    document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
    document.querySelector("#contact_about").innerHTML = data.description;
    const contactFavorite = document.querySelector("#contact_favorite");
    if (data.favorite) {
      contactFavorite.innerHTML =
        "Favorite Contact <i class='fas fa-star text-yellow-400'></i>"
    } 
    else {
      contactFavorite.innerHTML = "Not Favorite Contact";
    }

    document.querySelector("#contact_website").href = data.websiteLink; //to make it as link
    document.querySelector("#contact_website").innerHTML = data.websiteLink; //to show on page
    document.querySelector("#contact_linkedIn").href = data.linkedInLink;
    document.querySelector("#contact_linkedIn").innerHTML = data.linkedInLink;
        
        openContactModal();
    }
    catch(error){
        console.log("error: ",error);
    }
}

//delete contact popup
async function deleteContact(id){

    Swal.fire({
        title: "Do you want to delete the contact?",
        icon: "warning",
        showCancelButton: true,
        cancelButtonColor: "#899499",
        confirmButtonText: "Delete",
        confirmButtonColor: "#DD6B55"
      }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
          //our code
          const url=`${baseURL}/user/contacts/delete/`+id;
          window.location.replace(url);
        //  Swal.fire("Saved!", "", "success");
        } else if (result.isDenied) {
          Swal.fire("Changes are not saved", "", "info");
        }
        
      });
}



