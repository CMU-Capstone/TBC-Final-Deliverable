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

class Demo extends React.Component {
  // export default function Search() {

  constructor(props) {
    super(props);
    this.result1 = [];
    this.result2 = [];
    this.state = {
      isLoading: true,
      industry1: "",
      problem1: "",
      industry2: "",
      problem2: "",
    };
  }

  // componentWillMount = () => { 
  //   this.getQuestionsByProblems();
  // }

  getQuestionsByProblems = () => {
    const { problem2, industry2 } = this.state;
    fetch(process.env.REACT_APP_DEMO_BASE+`/demo_controller/api/getQuestionsByProblems?industry=${industry2}&problem=${problem2}`, {
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
        console.log(responseJson);
        
        Object.keys(responseJson)
        .forEach((key) => { 
          // console.log(key); // alerts key 
          //|| key=="Descriptions" || key=="Achievements" || key=="file_extension"
          let list = [];
          if (key == "Questions") {
            let user = responseJson[key];
            Object.keys(user)
              .forEach((userEmail) => { 
                // for every user
            

                // for every question
                // let id = 1;
                  Object.keys(user[userEmail])
                    .forEach((question) => { 
                      let list = [];
                      list.push(userEmail);
                      let text = "";
                      // console.log(user[userEmail][question]);
                      console.log(user[userEmail][question].text);
                      text = text.concat(user[userEmail][question].text);
                      // text=text.concat("");
                      list.push(text);
                      this.result1.push(list);
                    });
                
                
              });
       
            console.log(this.result2);
          } 
          
        });
        // this.options = responseJson;
        this.setState({ isLoading: false });
      })

        .catch(error => console.error(error));

  //  = ["BUIDL Online", "Hachthon1"];

  }

  getTechByProblems = () => {
    const { problem2, industry2 } = this.state;
    fetch(process.env.REACT_APP_DEMO_BASE+`/demo_controller/api/getTechByProblems?industry=${industry2}&problem=${problem2}`, {
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
        
        
        Object.keys(responseJson)
          .forEach((key) => { 
            // console.log(key); // alerts key 
            //|| key=="Descriptions" || key=="Achievements" || key=="file_extension"
            let list = [];
            if (key != "problem") {
              list.push(key);
              list.push(responseJson[key]);
              this.result2.push(list);
            } 
            
          });
        
          this.setState({ isLoading: false });
        console.log(this.result2);
        })
        .catch(error => console.error(error));

  //  = ["BUIDL Online", "Hachthon1"];

  }

  renderResult = isLoading => {
    if (isLoading) {
      return (<Typography> </Typography>);
    }
     return(
      <Card>
        <CardHeader color="primary">
          <h4 className={makeStyles.cardTitleWhite}>Slack Questions</h4>
        </CardHeader>
         <CardBody> 
           <Table style={{height: 10,overflow: "scroll"}}
             tableHeaderColor="primary"
            //"Descriptions","Achievements","File Extension"
            tableHead={["User Name","Questions"]}
            tableData={this.result1}
          />
        </CardBody>
      </Card>
    );
  }


  renderResult2 = isLoading => {
    if (isLoading) {
      return (<Typography> </Typography>);
    }
     return(
      <Card>
        <CardHeader color="primary">
          <h4 className={makeStyles.cardTitleWhite}>Technology</h4>
        </CardHeader>
         <CardBody> 
           <Table style={{height: 10,overflow: "scroll"}}
             tableHeaderColor="primary"
            //"Descriptions","Achievements","File Extension"
            tableHead={["Technology Name","Quantity"]}
            tableData={this.result2}
          />
        </CardBody>
      </Card>
    );
  }


  render() {
 
    const { isLoading,industry2,problem2 } = this.state;
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
                    <TextField
                      required
                      fullWidth
                      label="Industry"
                      id="Name"
                      formControlProps={{
                        fullWidth: true
                      }}
                      onChange={event =>{
                        this.setState({ industry2: event.target.value });
                      }}
                    />
                    {'\t'}
                    <TextField
                      required
                      label="Problem"
                      id="Name"
                      fullWidth
                      formControlProps={{
                        fullWidth: true
                      }}
                      onChange={event =>{
                        this.setState({ problem2: event.target.value });
                      }}
                    />
                  </GridItem>
        
                </GridContainer>
          
                
              </CardBody>
              <CardFooter>
                <Button color="primary" onClick={() => {
                  if (industry2=="" || problem2=="") {
                    alert("Please input required data.");
                  } else {
                    this.getTechByProblems();
                    this.getQuestionsByProblems();
                  }
                }}>Search</Button>
              </CardFooter>
            </Card>
          </GridItem>
          <GridItem xs={12} sm={12} md={6}>
            {this.renderResult(isLoading)}
          </GridItem>
          <GridItem xs={12} sm={12} md={6}>
            {this.renderResult2(isLoading)}
          </GridItem>
        </GridContainer>
      </div>
    );
  }
}

export default Demo;
