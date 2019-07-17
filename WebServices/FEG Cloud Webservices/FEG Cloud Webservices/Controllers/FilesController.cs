using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Http.Internal;
using Microsoft.AspNetCore.Mvc;

namespace FEG_Cloud_Webservices.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FilesController : ControllerBase
    {
        // GET api/files
        [HttpGet("{id}")]
        public ActionResult<IEnumerable<string>> Get(string id)
        {
            
            Request.EnableRewind();
            using (var reader = new StreamReader(Request.Body))
            {
                var body = reader.ReadToEnd();

                // Do something

                Request.Body.Seek(0, SeekOrigin.Begin);

                body = reader.ReadToEnd();
            }
            return new string[] { "value1", "value2" };
        }


        // POST api/files
        [HttpPost]
        public ActionResult<IEnumerable<string>> Post([FromBody] string value)
        {
            return new string[] { "value1", "value2" };
        }
    }
}
