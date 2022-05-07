import { Component } from 'react';
import { Link, useNavigate } from "react-router-dom";
import '../style/style.css';

class SignUpImpl extends Component {

    AdminSignatoryInfoCreatePage = () => {

        const navigate = useNavigate();

        //after submit form redirect user
        navigate.push('login');
    }

    constructor(props) {
        super(props);
        this.state = {
            image: null
        };

        this.onImageChange = this.onImageChange.bind(this);
        this.doSignup = this.doSignup.bind(this);
    }

    doSignup(ev) {
        ev.preventDefault();

        let form = document.querySelector('#signupForm');
        fetch("/api/auth/signup", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: form.username.value,
                password: form.password.value,
                email: form.email.value,
                phoneNumber: null
            })
        })
            .then(async response => {
                if (!response.ok) {
                    const json = await response.json();
                    let e = new Error();
                    e.json = json;
                    throw e;
                }
                alert("Velkommen! Du kan nå logge inn.");
                this.props.navigate("/pages/login");
            })
            .catch((error) => {
                try {
                    // I'm reasonably certain this is a trash way to handle this.
                    // TODO: handle in sprint 2
                    let errMsg = error.json.message || error.json.error;
                    console.log(errMsg);
                    // Not really sure if we need an entire switch cascade; not sure off the top of my head what
                    // the possible errors are. We also need to account for various other errors.
                    switch (errMsg) {
                    case "Unauthorized":
                        // TODO: find a better way to handle errors
                        alert("Invalid username and/or password");
                        break;
                    case null:
                        alert("An unknown error occurrred");
                        break;
                    default:
                        alert(errMsg);
                        break;
                    }
                } catch(e) {
                    alert("An unknown error occurred. Try again later.");
                }
            });
    }


    onImageChange = event => {
        if (event.target.files && event.target.files[0]) {
            let img = event.target.files[0];
            this.setState({
                          image: URL.createObjectURL(img)
            });
        }
    };


    render() {
        return (
            <div class="container">
                <h1>Registrer deg</h1>
                <form onSubmit={this.doSignup} id="signupForm">
                    <br></br>
                    <label htmlFor="username">Brukernavn</label>
                    <br></br>
                    <input required type="text" placeholder='OlaNormann12' id="username" name="username"/>


                    <br></br>
                    <label htmlFor="email">Email</label>
                    <br></br>
                    <input required type="text" placeholder='olanormann@gmail.com' id="email" name="email"/>


                    <br></br>
                    <label htmlFor="password">Passord</label>
                    <br></br>
                    <input required type="password" placeholder='************' id="password" name="password"/>


                    <br></br>
                    <label htmlFor="repeatPassword">Gjenta passord</label>
                    <br></br>
                    <input type="password" placeholder='************' id="repeatPassword" name="passord"/>



                    <br></br>
                    {/*<label htmlFor="img">Bilde</label>
                    <br></br>
                    <img width="300px" src={this.state.image} alt="pb" />
                    <br></br>
                    <input type="file" id="myImage" name="myImage" onChange={this.onImageChange} />
                    */}
                    <br></br>
                    <label htmlFor="phoneNumber">Telefonnummer</label>
                    <input type="number" min="10000000" max="99999999" name="phoneNumber" placeholder='92837823'/>

                    <br></br>
                    {/* <Link to="../pages/explore">
                </Link> */}
                    <button type="submit">
                        Registrer
                    </button>
                </form>
                <Link to="/" id="tilbake">Tilbake</Link>
            </div>
        );
    };
}
const SignUp = props => {
    return <SignUpImpl {...props} navigate={useNavigate()} />;
};

export default SignUp;
