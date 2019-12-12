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
import ReactDOM from "react-dom";
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

class Update extends React.Component {
  // export default function Search() {

  constructor(props) {
    super(props);
    this.state = {
      isLoading: true,
      csvData:"",
    };
  }

  componentWillMount = () => { 

  }

  setCSV = (text) =>{
    this.setState({
      csvData:text,
    });
  }

  // {
  //   "gitRepos": [
  //     "string"
  //   ],
  //   "hackthon_name": "string"
  // }

  updateGit =(text)=>{
    // console.log(text);
    let map={};
   
    let gitURLs=text.split("\n");
   
      Object.keys(gitURLs)
        .forEach((key) => { 
          // console.log(gitURLs[key]);
          let line=gitURLs[key].split(",");
          if(line[1]!= undefined && line[1]!=="" && line[1].includes("github.com")){
            if(line[0] in map){
              map[line[0]].push(line[1]);
            }else{
              map[line[0]]=[line[1]];
            }
          }
      })
    
  // console.log(map);
  let count=1;
  let length=Object.keys(map).length;
  console.log(length);
  Object.keys(map)
  .forEach((key) => { 
    // console.log(map[key]);
    // console.log(key);
    this.updateGitAPI(length,map[key],key,count);
    count+=1;

  });

 
  
  }

  updateGitAPI =(length,gitURLs,hackthon_name,count)=>{
    const postPara = {
      gitRepos: gitURLs,
      hackthon_name,
    };
  
    // console.log(JSON.stringify(postPara));

    // alert(JSON.stringify(searchPara));
    fetch(process.env.REACT_APP_GIT_BASE+`/github_scanner/api/add`, {
      // mode: "no-cors",
      method: 'POST',
      headers: {
        Origin:'*',
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(postPara)
    })
    .then((response) => {
      console.log(count);
      console.log(length);
      if (response.status === 200 && count===length) {
        alert("Upload succeed!");
      } else if(count===length){
        alert("Upload failed! Please try again later.");
      }

      
      
    })
      .catch(error => console.error(error));
  
  
  }

  render() {
    let fileInputRef = React.createRef();
    const { isLoading,csvData } = this.state;
    return (
      <div>
        <GridContainer>
          <GridItem xs={12} sm={12} md={12}>
            <Card>
              <CardHeader color="primary">
                <h4 className={makeStyles.cardTitleWhite}>Add Projects</h4>
              </CardHeader>
              <CardBody>
                <GridContainer>
                  <GridItem xs={12} sm={12} md={12}>
                  <input
                    type="file"
                    ref={fileInputRef}
                    style={{ display: "none" }}
                    onChange={async e => {
                      const reader = new FileReader();
                      reader.onload = () => {
                        const text = reader.result;
                        this.setCSV(text);
                        this.updateGit(text);
                        // alert(text);
                      };
                      reader.readAsText(e.target.files[0]);
                    }}
                    accept=".csv,application/csv"
                  />
                  <Button
                    onClick={() => {
                      fileInputRef.current.click();
                    }}
                  >
                    Upload CSV file
                  </Button>
                  </GridItem>
        
                </GridContainer>
                
              </CardBody>
              <CardFooter>
                {/* <Button color="primary" onClick={() => {
                  this.getProjects()
                }}>Search</Button> */}
              </CardFooter>
            </Card>
          </GridItem>
  
        </GridContainer>
      </div>
    );
  }
}

export default Update;
