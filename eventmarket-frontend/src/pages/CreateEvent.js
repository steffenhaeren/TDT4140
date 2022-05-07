import {Component} from 'react';
import {useNavigate, Navigate} from 'react-router-dom';
import Navbar from '../pages/Navbar.js';
import '../style/style.css';

class CreateEventImpl extends Component {

    state = {
        wish:"",
        overskrift:"",
        kategori:"",
        beskrivelse:"",
        pris: 0,
        lokasjon:"",
        telefonnummer: 0,
    };


    handleChange = (e) => {
        this.setState({
            wish: e.target.value
        });
    };

    constructor(props) {
        super(props);
        this.doCreateEvent = this.doCreateEvent.bind(this);
        this.state = {
            image: null
        };

        this.onImageChange = this.onImageChange.bind(this);
    }

    doCreateEvent(ev) {
        ev.preventDefault();
        console.log(ev)
        let form = ev.target;
        console.log(form);

        if (!form.eventType.value) {
            alert("Please select an event type");
            return;
        }

        fetch("/api/posts/publish", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + window.localStorage.getItem("bearer")
            },
            body: JSON.stringify({
                loc: {
                    venue: form.venue.value,
                    city: form.city.value
                },
                headline: form.headline.value,
                eventType: form.eventType.value,
                price: form.price.value,
                dateAndTime: (new Date(form.date.value + " " + form.time.value).getTime() / 1000).toString()
            })
        })
            .then(async response => {
                if (!response.ok) {
                    let e = new Error();
                    e.json = await response.json();
                    throw e;
                }
                alert("Billetter publisert.");
                this.props.navigate("/pages/explore");
            })
            .catch((error) => {
                console.log(error);
                try {
                    let errMsg = error.error;
                    // Not really sure if we need an entire switch cascade; not sure off the top of my head what
                    // the possible errors are. We also need to account for various other errors.
                    switch (errMsg) {
                        case "Unauthorized":
                            // TODO: find a better way to handle errors
                            alert("Your token has expired. Refresh and try again.");
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




    onImageChange = event => {
        if (event.target.files && event.target.files[0]) {
            let img = event.target.files[0];
            this.setState({
                          image: URL.createObjectURL(img)
            });
        }
    };

    render() {
        let bearer = window.localStorage.getItem("bearer");
        if (bearer == null || bearer === "") {
            return <Navigate to="/" />
        }
        return (
            <div>
                <Navbar />
                <div className="parent">
                    <div className="child">
                        <h1>Publiser event</h1>
                        {/*
                        <label htmlFor="img">Legg til bilde: </label>
                        <br></br>
                        <img width="300px" alt="img" src={this.state.image} />
                        <br></br>
                        <input type="file" name="myImage" onChange={this.onImageChange} />
                        */ }
                    </div>
                    <div className="child">
                        <div id="form">
                            <form onSubmit={this.doCreateEvent}>
                            {/*<p>Jeg vil</p>
                            <input type="radio" value="kjøpe" id="kjøpe"
                                onChange={this.handleChange} name="wish" />
                            <label htmlFor="kjøpe">Kjøpe</label>

                            <input type="radio" value="selge" id="selge"
                                onChange={this.handleChange} name="wish"/>
                            <label htmlFor="selge">Selge</label>

                            <p>Du vil {this.state.wish}</p>*/}

                            <div className="float">
                                <label htmlFor="headline">Overskrift: </label>
                                <input required type="text" placeholder='SPIDERMAN BILLETT' id="headline" name="headline"/>

                                <br></br>
                                <br></br>
                                <label htmlFor="category">Kategori: </label>
                                <select required name="eventType" id="eventType">
                                    <option>Velg kategori</option>
                                    <option value="CONCERT">Konsert</option>
                                    <option value="CINEMA">Kino</option>  
                                    <option value="THEATRE">Teater</option>
                                    <option value="OTHER">Annet</option>
                                </select>

                                <br></br>
                                <br></br>
                                <label htmlFor="date">Dato: </label>
                                <input required type="date" id="date" name="date"/>

                                <br></br>
                                <br></br>
                                <label htmlFor="time">Tid: </label>
                                <input required type="time" id="time" name="time"/>

                                <br></br>
                                <br></br>
                                {/*<label htmlFor="beskrivelse">Beskrivelse: </label>
                                <textarea id="beskrivelse" name="beskrivelse"/>

                                <br></br> */ }
                                <br></br>
                                <label htmlFor="price">Pris: </label>
                                <input required  type="number" min="0" name="price" id="price" placeholder='Kr.'/>

                                <br></br>
                                <br></br>
                                <label htmlFor="by">By: </label>
                                <input required type="text" name="city" id="city" placeholder='Fredrikstad'/>
                                <br></br>
                                <br></br>
                                <label htmlFor="sted">Sted: </label>
                                <input required type="text" name="venue" id="venue" placeholder='Gamlebyen'/>

                                <br></br>
                                <br></br>
                                <label htmlFor="tlf">Tlf: </label>
                                <input type="number" min="10000000" max="99999999" name="telefonnummer" placeholder='92837823'/>

                            </div>

                            <br></br>
                            {/* <Link to="../pages/explore">
                </Link> */}
                            <button type="submit">
                                Publiser
                            </button>
                        </form>
                    </div>
                </div>
            </div>


        </div>
        );
    };
}
const CreateEvent = props => {
    return <CreateEventImpl {...props} navigate={useNavigate()} />;
};

export default CreateEvent;
