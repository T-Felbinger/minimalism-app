using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(ConnectWebPage.Startup))]
namespace ConnectWebPage
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
