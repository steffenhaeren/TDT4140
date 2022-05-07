import { Component } from 'react';
import { useNavigate, Navigate, Link } from 'react-router-dom';
import '../style/style.css';


class LogInImpl extends Component {

    constructor(props) {
        super(props);
        this.doLogin = this.doLogin.bind(this);
    }

    doLogin(ev) {
        ev.preventDefault();

        let form = document.querySelector('#loginForm');
        fetch("/api/auth/signin", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: form.username.value,
                password: form.password.value
            })
        })
            .then(async response => {
                if (!response.ok) {
                    const json = await response.json();
                    let e = new Error();
                    e.json = json;
                    throw e;
                }
                return response.json();
            })
            .then(json => {
                // Might need more storage here. We're discarding emails, the MongoDB ID
                window.localStorage.setItem("bearer", json.accessToken);
                window.localStorage.setItem("username", json.username);
                window.localStorage.setItem("roles", JSON.stringify(json.roles));
                window.localStorage.setItem("email", json.email);

                this.props.navigate("/pages/explore");
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
                    default:
                        alert("An unknown error occured.");
                        break;
                    }
                } catch(e) {
                    alert("An unknown error occured. Try again later.");
                }
            });
    }

    render() {

        let bearer = window.localStorage.getItem("bearer");
        if (bearer != null && bearer !== "") {
            return <Navigate to="/pages/explore" />
        }

        return (
        <div class="container">
            <h1>Logg inn</h1>
            <form onSubmit={this.doLogin} id="loginForm">
                <br></br>

                <br></br>
                <label htmlFor="username">Username</label>
                <br></br>
                <input required type="text" placeholder='OlaNordmann1234' id="username" name="username"/>

                <br></br>
                <label htmlFor="password">Passord</label>
                <br></br>
                <input required type="password" placeholder='************' id="password" name="passord"/>

                <br></br>
                <button type="submit">Logg inn</button>
            </form>
            <Link to="/">Tilbake</Link>
        </div>
        );
    }
}

const LogIn = props => {
    return <LogInImpl {...props} navigate={useNavigate()} />;
};

export default LogIn;
