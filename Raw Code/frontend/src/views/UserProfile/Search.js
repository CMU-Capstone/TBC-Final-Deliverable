import React from "react";
// @material-ui/core components
import { makeStyles } from "@material-ui/core/styles";
import TextField from '@material-ui/core/TextField';
// Material helpers
// import { withStyles } from "@material-ui/core";
import InputLabel from "@material-ui/core/InputLabel";
import Select from 'react-select';

// core components
import GridItem from "components/Grid/GridItem.js";
import GridContainer from "components/Grid/GridContainer.js";
import CustomInput from "components/CustomInput/CustomInput.js";
import Button from "components/CustomButtons/Button.js";
import Card from "components/Card/Card.js";
import CardHeader from "components/Card/CardHeader.js";
import CardAvatar from "components/Card/CardAvatar.js";
import CardBody from "components/Card/CardBody.js";
import CardFooter from "components/Card/CardFooter.js";
import Table from "components/Table/Table.js";
import avatar from "assets/img/faces/marc.jpg";
import { Typography } from "@material-ui/core";

const styles = {
  cardCategoryWhite: {
    color: "rgba(255,255,255,.62)",
    margin: "0",
    fontSize: "14px",
    marginTop: "0",
    marginBottom: "0"
  },
  cardTitleWhite: {
    color: "#FFFFFF",
    marginTop: "0px",
    minHeight: "auto",
    fontWeight: "300",
    fontFamily: "'Roboto', 'Helvetica', 'Arial', sans-serif",
    marginBottom: "3px",
    textDecoration: "none"
  }
};

class Search extends React.Component {
  // export default function Search() {

  constructor(props) {
    super(props);
    this.projectData = [];
    this.options = [];
    this.state = {
      isLoading: true,
      hackthon_name: [],
      project_name: null,
      problem: null,
      industry: null,
      technology: null,
      email: null,
      header: null,
      data: null
    };
  }

  componentWillMount = () => { 
    this.getHackthons();
  }
  getHackthons = () => {
    fetch(process.env.REACT_APP_GIT_BASE+`/github_scanner/api/all`, {
      // mode: "no-cors",
      method: 'GET',
      headers: {
        Origin:'*',
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
    })
      .then(response => response.json())
      .then((responseJson) => {
        // console.log(responseJson);
        // this.options = responseJson;
        responseJson.map((op) => {
          // console.log(op);
          this.options.push({
            value: op,
            label: op
          });
        });
      // for (let i = 0; i < responseJson.length; i++){ 
      //   Object.keys(responseJson[i])
      //     .forEach((key) => { 
      //       this.options.push(key);
      //      });
      //   }
        })
        .catch(error => console.error(error));

  //  = ["BUIDL Online", "Hachthon1"];

  }
  /**
   * Retrieve projects.
   *  @param {string} queryParam
   */
  getProjects = () => {
    this.projectData = [];
    const { hackthon_name, project_name, problem, industry, technology, email, header, data } = this.state;
    console.log(hackthon_name);
    // options[0].value
    let hackthon_name_query = []
    if (hackthon_name != null) {
      for (let i = 0; i < hackthon_name.length; i++) {
        hackthon_name_query.push(hackthon_name[i].value);
      }
    }
    const searchPara = {
      hackthon_name:hackthon_name==""?null:hackthon_name_query,
      project_name:project_name==""?null:project_name,
      problem:problem==""?null:problem,
      industry:industry==""?null:industry,
      technology:technology==""?null:technology,
      email:email==""?null:email,
      header:header==""?null:header,
      data:data==""?null:data
    };



    // alert(JSON.stringify(searchPara));
    // http://18.220.249.85:8082/github_scanner/api
    fetch(process.env.REACT_APP_GIT_BASE+`/github_scanner/api/search`, {
      // mode: "no-cors",
      method: 'POST',
      headers: {
        Origin:'*',
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(searchPara)
    })
      .then(response => response.json())
    .then((responseJson) => {
      for (let i = 0; i < responseJson.length; i++){ 
        let list = [];
        Object.keys(responseJson[i])
          .forEach((key) => { 
            // console.log(key); // alerts key 
            //|| key=="Descriptions" || key=="Achievements" || key=="file_extension"
            
            if(key=="hackthon_name"|| key=="project_name"|| key=="problem"|| key=="industry"|| key=="technology"||key=="email" ){
              list.push(responseJson[i][key]);
            } else {
              // list.push(key);
            }
            // console.log(responseJson[i][key]); // alerts value
          });
        // console.log(list);
        this.projectData.push(list);
        console.log(typeof (this.projectData));
      }
      // console.log(this.projectData);

        this.setState({ isLoading: false });
        // alert(this.projectData.length);
      })
      .catch(error => console.error(error));
  }

  renderResult = isLoading => {
    if (isLoading) {
      return (<Typography> </Typography>);
    }
     return(
      <Card>
        <CardHeader color="primary">
          <h4 className={makeStyles.cardTitleWhite}>Search Result</h4>
        </CardHeader>
         <CardBody> 
           <Table style={{height: 10,overflow: "scroll"}}
             tableHeaderColor="primary"
            //"Descriptions","Achievements","File Extension"
            tableHead={["Hackthon Name","Project Name", "Problem", "Industry", "Technology","Email"]}
            tableData={this.projectData}
          />
        </CardBody>
      </Card>
    );
  }

  render() {
    const { isLoading,hackthon_name } = this.state;
    return (
      <div>
        <GridContainer>
          <GridItem xs={12} sm={12} md={12}>
            <Card>
              <CardHeader color="primary">
                <h4 className={makeStyles.cardTitleWhite}>Search Projects</h4>
              </CardHeader>
              <CardBody>
                <GridContainer>
                  <GridItem xs={12} sm={12} md={12}>
                  {/* const { hackthon_name, project_name, problem, industry, technology, email, header, data } = this.state; */}

                  <Select 
                      isMulti
                      onChange={(options) => {
                        // console.log(options[0].value);
                        this.setState({
                          hackthon_name: options,
                        });
            
                      }}
                      options={this.options}
                      placeholder="Hackthon Name"
                      value={hackthon_name}
                    />
                    {/* <TextField
                      label="Hackthon Name"
                      id="Name"
                      formControlProps={{
                        fullWidth: true
                      }}
                      onChange={event =>{
                        this.setState({ hackthon_name: event.target.value });
                      }}
                    /> */}
                    {'\t'}{'\t'}
                    <TextField
                      label="Project Name"
                      id="Name"
                      fullWidth
                      formControlProps={{
                        fullWidth: true
                      }}
                      onChange={event =>{
                        this.setState({ project_name: event.target.value });
                      }}
                    />
                  </GridItem>
                  </GridContainer>
                  <GridContainer>
                  <GridItem xs={12} sm={12} md={12}>
                    <TextField
                      fullWidth
                      label="Problem"
                      id="Name"
                      formControlProps={{
                        fullWidth: true
                      }}
                      onChange={event =>{
                        this.setState({ problem: event.target.value });
                      }}
                    />
                    {'\t'}
                    <TextField
                      fullWidth
                      label="Industry"
                      id="Name"
                      formControlProps={{
                        fullWidth: true
                      }}
                      onChange={event =>{
                        this.setState({ industry: event.target.value });
                      }}
                    />
                  </GridItem>
                </GridContainer>

                <GridContainer>
                  <GridItem xs={12} sm={12} md={12}>
                    <TextField
                      fullWidth
                      label="Technology"
                      id="Name"
                      formControlProps={{
                        fullWidth: true
                      }}
                      onChange={event =>{
                        this.setState({ technology: event.target.value });
                      }}
                    />
                    {'\t'}
                    <TextField
                      label="Email"
                      id="Name"
                      fullWidth
                      formControlProps={{
                        fullWidth: true
                      }}
                      onChange={event =>{
                        this.setState({ industry: event.target.value });
                      }}
                    />
                  </GridItem>
        
                </GridContainer>
          
                <GridContainer>
                  <GridItem xs={12} sm={12} md={12}>
                  <TextField
                      label="Header"
                      id="Name"
                      fullWidth
                      formControlProps={{
                        fullWidth: true
                      }}
                      onChange={event =>{
                        this.setState({ industry: event.target.value });
                      }}
                    />
                     {'\t'}
                    <TextField
                      label="Data"
                      id="Name"
                      fullWidth
                      formControlProps={{
                        fullWidth: true
                      }}
                      onChange={event =>{
                        this.setState({ data: event.target.value });
                      }}
                    />
                  </GridItem>
                  
        
                </GridContainer>
                
              </CardBody>
              <CardFooter>
                <Button color="primary" onClick={() => {
                  this.getProjects()
                }}>Search</Button>
              </CardFooter>
            </Card>
          </GridItem>
          <GridItem xs={12} sm={12} md={12}>
            {this.renderResult(isLoading)}
          </GridItem>
        </GridContainer>
      </div>
    );
  }
}

export default Search;
