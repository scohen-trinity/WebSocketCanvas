console.log("Running React MessageList")

const ce = React.createElement

const csrfToken = document.getElementById("csrfToken").value;
const validateRoute = document.getElementById('validateRoute').value;
const generalMessagesRoute = document.getElementById("generalMessagesRoute").value;
const personalMessagesRoute = document.getElementById("personalMessagesRoute").value;
const addGeneralRoute = document.getElementById("addGeneralRoute").value;
const addPersonalRoute = document.getElementById("addPersonalRoute").value;
const deleteGeneralRoute = document.getElementById("deleteGeneralRoute").value;
const createRoute = document.getElementById("createRoute").value;

class MessageListMainComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedIn: false,
        }
    }

    render() {
        if(this.state.loggedIn) {
            return ce(MessageList, { doLogout: () => this.setState({loggedIn: false})});
        } else {
            return ce(LoginComponent, { doLogin: () => this.setState({ loggedIn: true })});
        }
    }
}

class LoginComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loginName: "", 
            loginPass: "", 
            createName: "", 
            createPass: "",
            loginMessage: "",
            createMessage: "",
        };
    }
    
    render() {
        return ce('div', null, 
            ce('h2', null, 'Login:'),
            ce('br'),
            'Username ',
            ce('input', {type: "text", id: "loginName", value: this.state.loginName, onChange: e => this.onChangeHandler(e)}),
            ce('br'),
            'Password: ',
            ce('input', {type: "password", id: "loginPass", value: this.state.loginPass, onChange: e => this.onChangeHandler(e)}),
            ce('br'),
            ce('button', {onClick: e=> this.login(e)}, 'Login'),
            ce('span', {id: "login-message"}, this.state.loginMessage),
            ce('h2', null, 'Create user: '),
            ce('br'),
            'Username: ',
            ce('input', {type: "text", id: "createName", value: this.state.createName, onChange: e => this.onChangeHandler(e)}),
            ce('br'),
            'Password: ',
            ce('input', {type: "password", id: "createPass", value: this.state.createPass, onChange: e => this.onChangeHandler(e)}),
            ce('br'),
            ce('button', {onClick: e => this.createUser(e)}, 'Create User'),
            ce('span', {id: "create-message"}, this.state.createMessage),
        )
        
    }

    onChangeHandler(e) {
        this.setState({ [e.target['id']]: e.target.value })   
    }

    login(e) {
        const username = this.state.loginName;
        const password = this.state.loginPass;

        fetch(validateRoute, {
            method: 'POST',
            headers: {'Content-Type': 'application/json', 'Csrf-Token': csrfToken },
            body: JSON.stringify({ username, password })
        }).then(res => res.json()).then(data => {
            if(data) {
                console.log("Logged in");
                this.setState({ loginName: "", loginPass: ""});
                this.props.doLogin();
            } else {
                this.setState({ loginMessage: "Login Failed" });
            }
        })
    }

    createUser(e) {
        const username = this.state.createName;
        const password = this.state.createPass;

        fetch(createRoute, {
            method: 'POST',
            headers: {'Content-Type': 'application/json', 'Csrf-Token': csrfToken },
            body: JSON.stringify({ username, password })
        }).then(res => res.json()).then(data => {
            if(data) {
                console.log("Created user");
                this.setState({ loginName: "", loginPass: ""});
                this.props.doLogin();
            } else {
                this.setState({ createMessage: "User Creation Failed" });
            }
        })
    }
}

class MessageList extends React.Component {
    constructor(props) {
        super(props);
        this.state = { generalMessages: [], personalMessages: [], newGeneral: "", newPersonal: "", sender: "" };
    }

    componentDidMount () {
        this.loadGeneralMessages();
        this.loadPersonalMessages();
    }

    render() {
        return ce('div', null, 
            ce('h2', null, 'General Message List'),
            ce('ul', null,
                this.state.generalMessages.map((generalMessage, index) => ce('li', { key: index, onClick: e => this.handleDeleteGeneral(index) }, generalMessage)),
            ),
            ce('br'),
            ce('div', null, 
                ce('input', {type: 'text', placeholder: 'Send a general message', value: this.state.newGeneral, onChange: e => this.handleGeneralChange(e)}),
                ce('br'),
                ce('br'),
                ce('button', { onClick: e => this.handleGeneralClick(e) }, 'Send General Message')
            ),
            ce('br'),
            ce('h2', null, 'Personal Message List'),
            ce('ul', null,
                this.state.personalMessages.map((personalMessage, index) => ce('li', { key: index }, personalMessage)),
            ),
            ce('div', null, 
                ce('input', {type: 'text', placeholder: 'Your message', value: this.state.newPersonal, onChange: e => this.handlePersonalChange(e)}),
                ce('br'),
                ce('br'),
                ce('input', {type: 'text', placeholder: 'Who to send to', value: this.state.sender, onChange: e => this.handleSenderChange(e)}),
                ce('br'),
                ce('br'),
                ce('button', { onClick: e => this.handlePersonalClick(e) }, 'Send Personal Message')
            ),
            ce('br'),
            ce('button', { onClick: e => this.props.doLogout() }, 'Logout'),
        );
    }

    loadGeneralMessages() {
        fetch(generalMessagesRoute).then(res => res.json()).then(generalMessages => this.setState({ generalMessages }));
    }

    loadPersonalMessages() {
        fetch(personalMessagesRoute).then(res => res.json()).then(personalMessages => this.setState({ personalMessages }));
    }

    handleGeneralChange(e) {
        this.setState({newGeneral: e.target.value})
    }

    handlePersonalChange(e) {
        this.setState({newPersonal: e.target.value})
    }

    handleSenderChange(e) {
        this.setState({sender: e.target.value})
    }

    handleGeneralClick(e) {
        fetch(addGeneralRoute, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', 'Csrf-Token': csrfToken },
            body: JSON.stringify(this.state.newGeneral)
        }).then(res => res.json()).then(data => {
            if(data) {
                this.loadGeneralMessages();
                this.setState({ newGeneral: "" })
            } else {

            }
        })
    }

    handlePersonalClick(e) {
        const dataToSend = {
            newPersonal: this.state.newPersonal,
            sender: this.state.sender
          };

        fetch(addPersonalRoute, {
            method: 'POST',
            headers: {'Content-Type': 'application/json', 'Csrf-Token': csrfToken },
            body: JSON.stringify(dataToSend)
        }).then(res => res.json()).then(data => {
            if(data) {
                console.log(data);
                console.log("success")
                this.loadPersonalMessages();
                this.setState({ newPersonal: "", sender: "" });
            } else {
                console.log("failure");
            }
        })
    }

    handleDeleteGeneral(i) {
        fetch(deleteGeneralRoute, {
            method: 'POST',
            headers: {'Content-Type': 'application/json', 'Csrf-Token': csrfToken },
            body: JSON.stringify(i)
        }).then(res => res.json()).then(data => {
            this.loadGeneralMessages();
            this.setState({ generalMessage: "" })
        })
    }
}

ReactDOM.render(
    ce('div', null, 
        ce(MessageListMainComponent, null, null)
    ),
    document.getElementById('react-root')
);