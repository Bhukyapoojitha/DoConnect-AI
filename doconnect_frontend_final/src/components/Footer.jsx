import { Link } from "react-router-dom";

const Footer = () => {
  return (
    <footer
      style={{
        background: "#0F172A",
        color: "#CBD5E1",
        marginTop: "4rem",
        padding: "3rem 0 1rem"
      }}
    >
      <div className="container">

        <div className="row">

          <div className="col-md-4 mb-4">
            <h4 style={{ color: "#fff", fontWeight: "800" }}>
              🔗 DoConnect AI
            </h4>
            <p>
              Intelligent discussion and knowledge collaboration platform
              powered by AI.
            </p>
          </div>

          <div className="col-md-2 mb-4">
            <h6 style={{ color: "#fff" }}>Platform</h6>
            <div><Link to="/questions">Questions</Link></div>
            <div><Link to="/ask">Ask Question</Link></div>
            <div><Link to="/chat">Chat</Link></div>
          </div>

          <div className="col-md-2 mb-4">
            <h6 style={{ color: "#fff" }}>Community</h6>
            <div><Link to="/tags">Tags</Link></div>
            <div><Link to="/leaderboard">Leaderboard</Link></div>
          </div>

          <div className="col-md-2 mb-4">
            <h6 style={{ color: "#fff" }}>Support</h6>
            <div><a href="/">Help Center</a></div>
            <div><a href="/">Contact</a></div>
          </div>

          <div className="col-md-2 mb-4">
            <h6 style={{ color: "#fff" }}>Legal</h6>
            <div><a href="/">Privacy</a></div>
            <div><a href="/">Terms</a></div>
          </div>

        </div>

        <hr style={{ borderColor: "#334155" }} />

        <div
          style={{
            textAlign: "center",
            fontSize: "14px"
          }}
        >
          © 2026 DoConnect AI • Built with React & Spring Boot
        </div>

      </div>
    </footer>
  );
};

export default Footer;