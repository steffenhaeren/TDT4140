import {Component} from 'react';
import {Navigate, useLocation} from 'react-router-dom';
import Navbar from '../pages/Navbar.js';
import '../style/style.css';


class ExploreImpl extends Component {
    constructor(props) {
        super(props);
        this.state = {
            posts: []
        };
    }

    fetchPosts(page) {
        if (page == null || isNaN(page) || page < 0) {
            page = 0;
        }
        console.log("Fetching " + page);
        fetch("/api/posts/get?page=" + page, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + window.localStorage.getItem("bearer")
            }
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
                this.setState({ posts: json.ads });
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
                        // This won't trigger for normal users
                        alert("Your session has expired.");
                        break;
                    default:
                        alert("An unknown error occured.");
                        break;
                    }
                } catch(e) {
                    alert("An unknown error occurred. Try again later.");
                }
            });
    }

    componentDidMount() {
        this.fetchPosts(new URLSearchParams(this.props.location.search).get("page"));
    }
  
    render() {
        // This is admittedly client-side validation, but all the APIs are still hidden
        // behind security, so the forum itself is still protected.
        //
        // The redirect is largely to make sure we don't accidentally show legitimate
        // users a broken page that won't ever load.
        let bearer = window.localStorage.getItem("bearer");
        if (bearer == null || bearer === "") {
            return <Navigate to="/" />
        }

        return (
        <div>
            <Navbar />
            <div class="container">
                <h1>Explore</h1>

                <div class="event-list flex-card-container">
                    {this.state.posts.map(post => 
                        (<div class="flex-card">
                            <h3 class="event-name"><b>{post.location.city}</b>: {post.headline}</h3>
                            <div class="event-details">
                                <p class="seller-meta"><b>Selger</b>: {post.seller.username}</p>
                                <p><b>Type</b>: {post.typeOfEvent}</p>
                                <p><b>Tidspunkt</b>: {new Date(parseInt(post.dateAndTime)).toString()}</p>
                                <p class="event-location"><b>Sted</b>: {post.location.venue}</p>
                                <br />
                                {/*NOTE: This is where we integrate currencies, if we add support for multiple*/}
                                <p class="event-price"><b>Pris</b>: {post.price} NOK</p>
                            </div>
                        </div>)
                    )}
                </div>
            </div>
        </div>
        );
    };
}

const Explore = (props) => {
    return <ExploreImpl {...props} location={useLocation()} />;
};
  
export default Explore;
